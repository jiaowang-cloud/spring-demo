package jin.li.yun.com.common.config;

import com.alibaba.fastjson.JSON;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

/**
 * @author WangJiao
 * @since 2021/01/04
 */
@Configuration
@Slf4j
public class FeignConfiguration implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate requestTemplate) {
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (Objects.isNull(attributes)) {
      return;
    }
    HttpServletRequest request = attributes.getRequest();
    log.info("FeignConfiguration:Feign请求地址[apply:{}]", request.getRequestURL());
    Map<String, String[]> requestParam = request.getParameterMap();
    log.info("FeignConfiguration:请求参数[apply:{}]", JSON.toJSONString(requestParam));
    Enumeration<String> headerNames = request.getHeaderNames();
    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        String name = headerNames.nextElement();
        String values = request.getHeader(name);
        requestTemplate.header(name, values);
      }
    }
    log.info(
        "FeignConfiguration:Feign请求头[headers:{}]", JSON.toJSONString(requestTemplate.headers()));
  }
}
