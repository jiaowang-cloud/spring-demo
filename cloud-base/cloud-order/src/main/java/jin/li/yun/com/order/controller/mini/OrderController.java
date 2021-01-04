package jin.li.yun.com.order.controller.mini;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jin.li.yun.com.common.api.ApiResult;
import jin.li.yun.com.common.constant.Constant;
import jin.li.yun.com.common.entity.pojo.order.OrderPoJo;
import jin.li.yun.com.common.entity.request.OrderReq;
import jin.li.yun.com.order.service.OrderService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@RequestMapping(value = "/mini/order")
@RestController("miniOrderC")
@Api("MINI OrderAPI")
public class OrderController {
  @Resource private OrderService service;

  @PostMapping
  @ApiOperation("创建订单")
  public ApiResult create(
      @RequestParam(Constant.HeaderKey.USER_ID) long userId, @RequestBody OrderReq orderReq) {
    OrderPoJo of = OrderPoJo.of(orderReq);
    of.setUserId(userId);
    service.save(of);
    return ApiResult.of(of);
  }

  @GetMapping("/{id}")
  @ApiOperation("订单详情")
  public String detail(@PathVariable("id") long id) throws JSONException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("id", id);
    jsonObject.put("msg", "order 模块");
    return jsonObject.toString();
  }
}
