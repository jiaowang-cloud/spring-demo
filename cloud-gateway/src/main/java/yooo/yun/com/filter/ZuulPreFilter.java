package yooo.yun.com.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import yooo.yun.com.config.AppConfig;
import yooo.yun.com.utils.UrlCheckUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;

/**
 * @author WangJiao
 * @since 2020/11/10
 */
@Slf4j
@Component
public class ZuulPreFilter extends ZuulFilter {
  @Resource AppConfig appConfig;
  /**
   * PRE_TYPE = "pre：在请求被路由之前调用，可以使用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试Log等。<br>
   * ERROR_TYPE = "error"：将请求路由到对应的微服务，用于构建发送给微服务的请求。<br>
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
  public Object run() throws ZuulException {
    RequestContext requestContext = RequestContext.getCurrentContext();
    HttpServletRequest request = requestContext.getRequest();
    log.info("run:[method:{},url:{}]", request.getMethod(), request.getRequestURL());
    HttpServletResponse response = requestContext.getResponse();
    String allowHeaders =
        "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, Authorization";
    response.addHeader("Access-Control-Allow-Headers", allowHeaders);
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Credentials", "false");
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    if ("OPTIONS".equals(request.getMethod())) {
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
    return null;
  }
}
