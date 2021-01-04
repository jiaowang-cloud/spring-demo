package jin.li.yun.com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@MapperScan({"jin.li.yun.com.**.mapper"})
@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class OrderApplication {
  public static void main(String[] args) {
    SpringApplication.run(OrderApplication.class, args);
  }
}
