package jin.li.yun.com.producer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author WangJiao
 * @since 2020/11/10
 */
@Configuration
@RefreshScope
@Data
public class AppConfig {
  @Value("${zuul.white-pattern:ignore}")
  @Resource
  public String white_url;
}
