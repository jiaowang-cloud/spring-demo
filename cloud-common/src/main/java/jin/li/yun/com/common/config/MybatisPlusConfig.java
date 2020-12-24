package jin.li.yun.com.common.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jin.li.yun.com.common.handler.MetaHandler;

/**
 * MybatisPlus配置
 *
 * @author wangjiao
 * @since 2020/12/17
 */
@Configuration
public class MybatisPlusConfig {

  /**
   * 自动填充功能
   *
   * @return GlobalConfig
   */
  @Bean
  public GlobalConfig globalConfig() {
    GlobalConfig globalConfig = new GlobalConfig();
    globalConfig.setMetaObjectHandler(MetaHandler.of());
    return globalConfig;
  }
}
