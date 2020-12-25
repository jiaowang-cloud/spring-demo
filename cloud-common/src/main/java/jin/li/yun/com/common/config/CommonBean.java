package jin.li.yun.com.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjiao
 * @since 2020/12/24
 */
@Slf4j
@EnableSwagger2
@Configuration("ccb")
@SuppressWarnings("all")
public class CommonBean {
  @Value("${spring.swagger.enable:true}")
  private boolean enableSwagger;

  @Bean
  public Docket api() {

    ParameterBuilder tokenPar = new ParameterBuilder();
    List<Parameter> pars = new ArrayList<>();
    tokenPar
        .name("Authorization")
        .description("令牌")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .required(false)
        .build();
    pars.add(tokenPar.build());
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .enable(enableSwagger)
        .select()
        .apis(RequestHandlerSelectors.basePackage("jin.li.yun.com"))
        .paths(PathSelectors.any())
        .build()
        .globalOperationParameters(pars);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("项目接口文档")
        .description("项目描述")
        .contact(new Contact("wangjiao", "", ""))
        .version("2.0")
        .build();
  }
}
