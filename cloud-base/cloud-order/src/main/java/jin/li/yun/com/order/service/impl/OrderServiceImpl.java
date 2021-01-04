package jin.li.yun.com.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jin.li.yun.com.common.entity.pojo.order.OrderPoJo;
import jin.li.yun.com.common.service.Impl.BaseServiceImpl;
import jin.li.yun.com.order.mapper.OrderMapper;
import jin.li.yun.com.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangJiao
 * @since 2020/11/12
 */
@Slf4j
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, OrderPoJo>
    implements OrderService {
  @Resource private OrderMapper mapper;

  @Override
  public List<OrderPoJo> listByUserId(long userId) {
    return super.list(Wrappers.<OrderPoJo>lambdaQuery().eq(OrderPoJo::getUserId, userId));
  }
}
