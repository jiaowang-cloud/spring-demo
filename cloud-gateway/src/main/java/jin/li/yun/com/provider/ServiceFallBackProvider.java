package jin.li.yun.com.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 回退机制
 *
 * @author WangJiao
 * @since 2020/12/22
 */
@Slf4j
@Component
public class ServiceFallBackProvider implements FallbackProvider {
  /**
   * getRoute方法返回*表示对所有服务进行回退操作,如果只想对某个服务进行回退，<br>
   * 那么就返回需要回退的服务名称，这个名称一定要是注册到Eureka中的名称 <br>
   *
   * @return *
   */
  @Override
  public String getRoute() {
    log.info("getRoute:[返回星号表示对所有服务进行回退操作]");
    return "*";
  }


  /**
   * ClientHttpResponse构造回退内容
   *
   * @return clientHttpResponse
   */
  @Override
  public ClientHttpResponse fallbackResponse(String route, Throwable cause){
    log.info("fallbackResponse:[route:{}]",route);
    return new ClientHttpResponse() {
      /**
       * 通过getHeaders返回响应请求头信息
       *
       * @return httpHeaders
       */
      @Override
      public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        MediaType mt = new MediaType("application", "json", StandardCharsets.UTF_8);
        headers.setContentType(mt);
        return headers;
      }

      /**
       * 通过getBody返回回退的内容
       *
       * @return 响应流
       * @throws IOException 异常
       */
      @Override
      public InputStream getBody() throws IOException {
        if (Objects.nonNull(cause)) {
          log.error("getBody:[cause:{}]",cause.getMessage());
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        String body = JSON.toJSONString(getJsonObject());
        ctx.setResponseBody(body);
        return new ByteArrayInputStream(body.getBytes());
      }

      /**
       * 通过getStatusCode返回相应状态码
       *
       * @return httpStatus
       * @throws IOException 异常
       */
      @Override
      public HttpStatus getStatusCode() throws IOException {
        return HttpStatus.OK;
      }

      /**
       * 通过getRawStatusCode获取getStatusCode原始的状态码值
       *
       * @return value
       * @throws IOException 异常
       */
      @Override
      public int getRawStatusCode() throws IOException {
        return this.getStatusCode().value();
      }

      /**
       * 通过getStatusText返回相应状态码对应的文本
       *
       * @return msg
       * @throws IOException 异常
       */
      @Override
      public String getStatusText() throws IOException {
        return this.getStatusCode().getReasonPhrase();
      }

      @Override
      public void close() {}
    };
  }

  private JSONObject getJsonObject() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("code", "E2001");
    jsonObject.put("msg", "服务器内部错误");
    jsonObject.put("result", false);
    jsonObject.put("data", null);
    return jsonObject;
  }
}
