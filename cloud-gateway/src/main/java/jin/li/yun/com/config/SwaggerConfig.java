package jin.li.yun.com.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import jin.li.yun.com.common.constant.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 聚合文档配置 zuul routers 映射具体服务的/v2/api-docs swagger
 *
 * @author wangjiao
 * @since 2020/12/23
 */
@EnableSwagger2
@Component
@Primary
@Slf4j
public class SwaggerConfig implements SwaggerResourcesProvider {

  @Autowired private DiscoveryClient discoveryClient;

  @Value("${spring.application.name}")
  private String applicationName;

  @Override
  public List<SwaggerResource> get() {
    List<SwaggerResource> resources = new ArrayList<>();
    // 排除自身，将其他的服务添加进去
    discoveryClient.getServices().stream()
        .filter(s -> !s.equals(applicationName))
        .forEach(
            name -> {
                log.info("get:服务实例名称[names:{}]", name);
              String[] names = name.split(Constant.DEFAULT_STRING_SHORT_LINE);

              if (names.length == 2) {
                name = names[1];
                log.info("get:服务实例名称取后缀[name:{}]", name);
              }
              Optional<ServiceInstance> instanceOptional =
                  discoveryClient.getInstances(name).stream().findFirst();
              if (instanceOptional.isPresent()
                  && instanceOptional.get().getMetadata().containsKey("context-path")) {
                String contextPath = instanceOptional.get().getMetadata().get("context-path");
                  log.info("get:context-path[contextPath:{}]", contextPath);
                resources.add(
                    swaggerResource(name, "/" + name + contextPath + "/v2/api-docs", "2.0"));
              } else {
                resources.add(swaggerResource(name, "/" + name + "/v2/api-docs", "2.0"));
              }
            });

    return resources;
  }

  private SwaggerResource swaggerResource(String name, String location, String version) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    log.info("swaggerResource:[swaggerResource:{}]", JSON.toJSONString(swaggerResource));
    return swaggerResource;
  }
}
