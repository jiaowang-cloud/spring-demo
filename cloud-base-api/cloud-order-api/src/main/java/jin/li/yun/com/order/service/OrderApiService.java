package jin.li.yun.com.order.service;

import io.swagger.annotations.ApiOperation;
import jin.li.yun.com.common.api.ApiResult;
import jin.li.yun.com.common.constant.Constant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author WangJiao
 * @since 2020/12/31
 */
@FeignClient(value = Constant.ServerName.CLOUD_ORDER)
public interface OrderApiService {

  @GetMapping("/order/inner/order/list")
  @ApiOperation("设备查询订单列表")
  ApiResult listByUserId(@RequestParam(value = Constant.HeaderKey.USER_ID) final long userId);
}
