package yooo.yun.com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@MapperScan({"yooo.yun.com.**.mapper"})
@EnableEurekaClient
@SpringBootApplication
@EnableSwagger2
public class UserApplication {
  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}
