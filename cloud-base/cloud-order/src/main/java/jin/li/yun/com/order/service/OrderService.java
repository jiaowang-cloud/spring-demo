package jin.li.yun.com.order.service;

import jin.li.yun.com.common.entity.pojo.order.OrderPoJo;
import jin.li.yun.com.common.service.BaseService;

import java.util.List;

/**
 * @author WangJiao
 * @since 2021/01/04
 */
public interface OrderService extends BaseService<OrderPoJo> {
  /**
   * list by userId
   *
   * @param userId userId
   * @return list
   */
  List<OrderPoJo> listByUserId(long userId);
}
