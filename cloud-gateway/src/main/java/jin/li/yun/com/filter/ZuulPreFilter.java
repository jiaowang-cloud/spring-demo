package jin.li.yun.com.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import jin.li.yun.com.config.AppConfig;
import jin.li.yun.com.utils.UrlCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import jin.li.yun.com.common.constant.Constant;
import jin.li.yun.com.common.constant.Constant.HeaderKey;
import jin.li.yun.com.common.utils.JWTUtil;
import jin.li.yun.com.common.utils.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;

/**
 * @author WangJiao
 * @since 2020/11/10
 */
@Slf4j
@Component
public class ZuulPreFilter extends ZuulFilter {
  @Resource
  AppConfig appConfig;
  /**
   * PRE_TYPE = "pre：在请求被路由之前调用，可以使用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试Log等。<br>
   * ROUTE_TYPE = "route"：将请求路由到对应的微服务，用于构建发送给微服务的请求。<br>
   * POST_TYPE = "post"：在请求被路由到对应的微服务以后执行，可用来为Response添加HTTP Header、将微服务的Response发送给客户端等。<br>
   * ERROR_TYPE = "error"：在其他阶段发生错误时执行该过滤器<br>
   *
   * @return pre
   */
  @Override
  public String filterType() {
    return FilterConstants.PRE_TYPE;
  }

  /**
   * 指定该Filter执行的顺序（Filter从小到大执行） DEBUG_FILTER_ORDER = 1; FORM_BODY_WRAPPER_FILTER_ORDER = -1;
   * PRE_DECORATION_FILTER_ORDER = 5; RIBBON_ROUTING_FILTER_ORDER = 10; SEND_ERROR_FILTER_ORDER = 0;
   * SEND_FORWARD_FILTER_ORDER = 500; SEND_RESPONSE_FILTER_ORDER = 1000;
   * SIMPLE_HOST_ROUTING_FILTER_ORDER = 100; SERVLET_30_WRAPPER_FILTER_ORDER = -2;
   * SERVLET_DETECTION_FILTER_ORDER = -3;
   */
  @Override
  public int filterOrder() {
    return PRE_DECORATION_FILTER_ORDER - 3;
  }

  /** 指定需要执行该Filter的规则 返回true则执行run() 返回false则不执行run() */
  @Override
  public boolean shouldFilter() {
    RequestContext requestContext = RequestContext.getCurrentContext();
    Boolean isOpenAPi = requestContext.getBoolean("openapi", false);
    // 判断是外部接口则不执行run()，内部接口就执行run()
    log.info("shouldFilter:是否为开放平台接口[isOpenAPi:{}]", isOpenAPi);
    return !isOpenAPi;
  }

  @Override
  public Object run() {
    RequestContext requestContext = RequestContext.getCurrentContext();
    HttpServletRequest request = requestContext.getRequest();
    log.info("run:[method:{},url:{}]", request.getMethod(), request.getRequestURL());

    // 设置HttpServletRequest返回参数格式
    setHttpServletResponse(requestContext);

    if ("OPTIONS".equals(request.getMethod())) {
      // requestContext.setSendZuulResponse(Boolean.FALSE);告诉Zuul不需要将当前请求转发到后端的服务。
      requestContext.setSendZuulResponse(Boolean.FALSE);
      requestContext.setResponseStatusCode(HttpStatus.NO_CONTENT.value());
      return null;
    }

    String uri = request.getRequestURI();
    log.info("run:==========[uri:{}]", uri);
    if (UrlCheckUtil.isSkip(uri, appConfig.getWhite_url())) {
      log.info("run:白名单url,跳过token校验[{}]", uri);
      return null;
    }

    // 获取认证名称
    log.info("run:[开始校验token]");
    String token = request.getHeader("Authorization");
    log.info("run:[Authorization:{}]", token);
    boolean verified;
    if (StringUtils.isEmpty(token)) {
      log.warn("run:[请求token为空]");
      // requestContext.setSendZuulResponse(Boolean.FALSE);告诉Zuul不需要将当前请求转发到后端的服务。
      requestContext.setSendZuulResponse(Boolean.FALSE);
      requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
      // 通过setResponseBody返回数据给客户端
      requestContext.setResponseBody(JSON.toJSONString(getJsonObject()));
      requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
      return null;
    }

    // 用户请求时会在头部 Authorization 传给我之前存储的token, 我用来验证
    token = token.replace("Bearer ", "");
    log.info("run:[Authorization:{}]", token);
    // 校验token 正确性您没有该操作的权限
    log.info("run:[开始校验token]");
    verified = JWTUtil.verify(token);
    log.info("run:校验token end[verified:{}]", verified);
    if (verified) {
      JWTUtil.TokenEntity tokenEntity;
      tokenEntity = JWTUtil.getTokenEntity(token);
      log.info("run:前端token数据[{}]", tokenEntity);
      String redisToken = null;
      if (JWTUtil.LoginTypeEnum.SAAS.getValue().equalsIgnoreCase(tokenEntity.getLoginType())) {
        if (!checkSystem(uri, JWTUtil.LoginTypeEnum.SAAS)) {
          String json = JSON.toJSONString(getJsonNoPermissions());
          setRequestContext(requestContext, HttpStatus.FORBIDDEN, json);
          return null;
        }
        if ((tokenEntity.getRole() != JWTUtil.Role.ADMIN.getValue())) {
          log.info("run:[saas端权限不足]");
          String json = JSON.toJSONString(getJsonNoPermissions());
          setRequestContext(requestContext, HttpStatus.FORBIDDEN, json);
          return null;
        }
        // TODO: 2020/11/12/012 redisToken获取redis中的token
      } else if (JWTUtil.LoginTypeEnum.MI_NI
          .getValue()
          .equalsIgnoreCase(tokenEntity.getLoginType())) {
        if (!checkSystem(uri, JWTUtil.LoginTypeEnum.MI_NI)) {
          String json = JSON.toJSONString(getJsonNoPermissions());
          setRequestContext(requestContext, HttpStatus.FORBIDDEN, json);
          return null;
        }
        // TODO: 2020/11/12/012 redisToken获取redis中的token
      }
      // 校验前端传来的token与redis中存的是否一致

      // 校验token正确性,并写入token信息
      // *******************开始拦截****************************
      if (uri.contains("/user/") || uri.contains("/order")) {
        if (Objects.isNull(tokenEntity.getUserId())) {

          log.info("run:[内部服务访问,token检验没有userId,不能访问]");
          requestContext.setSendZuulResponse(false);
          requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
          requestContext.setResponseBody("{\"code\":401,\"msg\":\"没有userId访问受限！\"}");
          requestContext.getResponse().setContentType("text/json;charset=UTF-8");
          return null;

        } else {
          putParam(tokenEntity, requestContext);
        }
      } else {
        // token失效了
        log.info("run:[token 令牌失效]");
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        requestContext.setResponseBody("{\"code\":401,\"msg\":\"令牌失效,请重新登录！\"}");
        requestContext.getResponse().setContentType("text/json;charset=UTF-8");
        return null;
      }
    } else {
      // token失效了
      log.info("run:[token校验失败]");
      requestContext.setSendZuulResponse(false);
      requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
      requestContext.setResponseBody("{\"code\":401,\"msg\":\"token校验失败！或失效,请重新登录！\"}");
      requestContext.getResponse().setContentType("text/json;charset=UTF-8");
      return null;
    }
    log.info("run:[校验token完毕]");
    return null;
  }

  private void setHttpServletResponse(RequestContext requestContext) {
    HttpServletResponse response = requestContext.getResponse();
    String allowHeaders =
        "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, Authorization";
    response.addHeader("Access-Control-Allow-Headers", allowHeaders);
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Credentials", "false");
    response.setContentType("application/json");
    // servlet 使用UTF-8 而不是默认的
    response.setCharacterEncoding("UTF-8");
    // 通过设置响应头控制浏览器以UTF-8的编码显示数据，让浏览器用utf8来解析返回的数据,如果不加这句话，那么浏览器显示的将是乱码
    response.setContentType("text/html;charset=UTF-8");
  }

  private void putParam(
      final JWTUtil.TokenEntity tokenEntity, final RequestContext requestContext) {
    final HttpServletRequest request = requestContext.getRequest();

    final Map<String, List<String>> queryParams =
        Optional.ofNullable(requestContext.getRequestQueryParams()).orElse(new HashMap<>(1));
    JSONObject requestBody;
    Object body = null;
    // 是否写入requestBody
    boolean postFormData =
        Optional.ofNullable(request.getContentType())
            .map(o -> o.contains(MediaType.MULTIPART_FORM_DATA_VALUE))
            .orElse(false);
    try {
      body =
          Optional.ofNullable(
                  JSONObject.parse(
                      URLDecoder.decode(
                          StreamUtils.copyToString(
                                  Optional.ofNullable(
                                          (InputStream) requestContext.get("requestEntity"))
                                      .orElse(request.getInputStream()),
                                  StandardCharsets.UTF_8)
                              .replace("%", "%25"),
                          "UTF-8")))
              .orElse(null);
    } catch (Exception e) {
      log.warn("run:[{}]", e.getMessage());
      log.error(e.getMessage(), e);
    }
    requestContext.addZuulRequestHeader(
        Constant.HeaderKey.ROLE, String.valueOf(tokenEntity.getRole()));
    requestContext.addZuulRequestHeader(HeaderKey.USER_ID, String.valueOf(tokenEntity.getUserId()));
    requestContext.addZuulRequestHeader(HeaderKey.OPEN_ID, String.valueOf(tokenEntity.getOpenId()));
    // 加入自定义参数
    queryParams.put(
        HeaderKey.ROLE, Collections.singletonList(String.valueOf(tokenEntity.getRole())));
    queryParams.put(
        HeaderKey.USER_ID, Collections.singletonList(String.valueOf(tokenEntity.getUserId())));
    queryParams.put(
        HeaderKey.OPEN_ID, Collections.singletonList(String.valueOf(tokenEntity.getOpenId())));
    requestContext.setRequestQueryParams(queryParams);
    final boolean putBody = !postFormData && (Objects.isNull(body) || body instanceof JSONObject);
    if (putBody) {
      requestBody = Optional.ofNullable(body).map(JSONObject.class::cast).orElse(new JSONObject());
      requestBody.put(HeaderKey.ROLE, tokenEntity.getRole());
      putIfAbsent(HeaderKey.USER_ID, tokenEntity.getUserId(), requestBody);
      putIfAbsent(HeaderKey.OPEN_ID, tokenEntity.getOpenId(), requestBody);
      byte[] requestEntityBytes = requestBody.toJSONString().getBytes(StandardCharsets.UTF_8);
      requestContext.setRequest(
          new HttpServletRequestWrapper(requestContext.getRequest()) {
            @Override
            public ServletInputStream getInputStream() {
              return new ServletInputStreamWrapper(requestEntityBytes);
            }

            @Override
            public int getContentLength() {
              return requestEntityBytes.length;
            }

            @Override
            public long getContentLengthLong() {
              return requestEntityBytes.length;
            }
          });
    }
  }

  /** 仅当{@code val}不为null时,执行put方法 */
  private void putIfAbsent(final String key, final Object val, JSONObject requestBody) {
    execIfAbsent(val, v -> requestBody.put(key, v));
  }
  /** 仅当参数不为null时,执行{@code consumer} */
  private void execIfAbsent(final Object obj, Consumer<Object> consumer) {
    if (Objects.nonNull(obj)) {
      consumer.accept(obj);
    }
  }

  private JSONObject getJsonObject() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("code", "E1001");
    jsonObject.put("msg", "认证凭证不能为空");
    jsonObject.put("result", false);
    jsonObject.put("data", null);
    return jsonObject;
  }

  private JSONObject getJsonNoPermissions() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("code", "403");
    jsonObject.put("msg", "权限不足，请联系管理员！");
    jsonObject.put("result", false);
    jsonObject.put("data", null);
    return jsonObject;
  }

  /**
   * 检测系统是否隔离访问 1.ma 直接放行 2.系统生成token和请求携带token的系统一致 3.通用common请求放行
   *
   * @param uri the uri
   * @param loginTypeEnum the login type enum
   * @return boolean
   */
  private Boolean checkSystem(String uri, JWTUtil.LoginTypeEnum loginTypeEnum) {
    String[] split = StringUtils.split(uri, "/");
    if ("ma".equalsIgnoreCase(split[0])) {
      return true;
    }
    return loginTypeEnum.getValue().equalsIgnoreCase(split[1])
        || Constant.COMMON.equalsIgnoreCase(split[1]);
  }

  /**
   * 设置头部
   *
   * @param requestContext the request context
   * @param httpStatus the http status
   * @param json the json
   */
  @SuppressWarnings("SameParameterValue")
  private void setRequestContext(
      RequestContext requestContext, HttpStatus httpStatus, String json) {
    requestContext.setSendZuulResponse(Boolean.FALSE);
    requestContext.setResponseStatusCode(httpStatus.value());
    requestContext.setResponseBody(json);
    requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
  }
}
