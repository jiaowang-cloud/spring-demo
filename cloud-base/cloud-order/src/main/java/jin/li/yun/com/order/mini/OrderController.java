package jin.li.yun.com.order.mini;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@RequestMapping(value = "/mini/order")
@RestController("miniOrderC")
@Api("MINI OrderAPI")
public class OrderController {
  @GetMapping("/{id}")
  @ApiOperation("订单详情")
  public String detail(@PathVariable("id") long id) throws JSONException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("id", id);
    jsonObject.put("msg", "order 模块");
    return jsonObject.toString();
  }
}
