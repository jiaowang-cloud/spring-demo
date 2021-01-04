package jin.li.yun.com.order.controller.inner;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jin.li.yun.com.common.api.ApiResult;
import jin.li.yun.com.common.constant.Constant;
import jin.li.yun.com.common.entity.pojo.order.OrderPoJo;
import jin.li.yun.com.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@RequestMapping(value = "/inner/order")
@RestController("innerOrderC")
@Api("INNER OrderAPI")
public class OrderController {
  @Resource private OrderService service;

  @GetMapping("/list")
  @ApiOperation("订单列表")
  public ApiResult listByUserId(@RequestParam(value = Constant.HeaderKey.USER_ID) long userId) {
    List<OrderPoJo> list = service.listByUserId(userId);
    return ApiResult.ok(list);
  }
}
