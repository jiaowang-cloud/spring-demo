package yooo.yun.com.common.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;
import yooo.yun.com.common.utils.SnowflakeIdWorkerUtil;

/**
 * 自定义ID生成器
 *
 * @author WangJiao
 * @since 2020/12/21
 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {

  @Override
  public Long nextId(Object entity) {

    return SnowflakeIdWorkerUtil.generateId();
  }
}
