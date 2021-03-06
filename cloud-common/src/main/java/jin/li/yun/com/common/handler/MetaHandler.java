package jin.li.yun.com.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 处理新增和更新的基础数据填充，配合BaseEntity和MyBatisPlusConfig使用
 *
 * @author wangjiao
 * @since 2020/1217
 */
@Component
public class MetaHandler implements MetaObjectHandler {

  /**
   * 新增数据执行
   *
   * @param metaObject metaObject
   */
  @Override
  public void insertFill(MetaObject metaObject) {
    this.setFieldValByName("createTime", new Date(), metaObject);
    this.setFieldValByName("updateTime", new Date(), metaObject);
  }

  /**
   * 更新数据执行
   *
   * @param metaObject metaObject
   */
  @Override
  public void updateFill(MetaObject metaObject) {
    this.setFieldValByName("updateTime", new Date(), metaObject);
  }

  public static MetaHandler of() {
    return new MetaHandler();
  }
}
