package jin.li.yun.com.common.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jin.li.yun.com.common.entity.BaseEntityResponse;
import jin.li.yun.com.common.entity.pojo.order.OrderPoJo;
import jin.li.yun.com.common.entity.pojo.user.UserPoJo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.Order;

/**
 * 订单信息Response
 *
 * @author WangJiao
 * @since 2021-01-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单信息Response")
public class OrderResponse extends BaseEntityResponse {

  @ApiModelProperty(value = "用户ID", example = "435677")
  private Long userId;

  @ApiModelProperty(value = "顾客ID", example = "45115454")
  private Long customerId;

  @ApiModelProperty(value = "订单号", example = "123123123")
  private String orderNum;

  public static OrderResponse of() {
    return new OrderResponse();
  }

  public static OrderResponse of(OrderPoJo poJo) {
    OrderResponse response = OrderResponse.of();
    // bean工具类，将一个实体对象属性赋值给另一个实体对象，这两个实体中的属性字段名称和字段类型必须保持一致，否则将拷贝不成功
    BeanUtils.copyProperties(poJo, response);
    return response;
  }
}
