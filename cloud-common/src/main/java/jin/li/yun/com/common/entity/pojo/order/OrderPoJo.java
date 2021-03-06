package jin.li.yun.com.common.entity.pojo.order;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jin.li.yun.com.common.entity.pojo.user.UserPoJo;
import jin.li.yun.com.common.entity.request.OrderReq;
import jin.li.yun.com.common.entity.request.UserReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jin.li.yun.com.common.entity.BaseEntity;
import org.springframework.beans.BeanUtils;

/**
 * 订单信息表
 *
 * @author WangJiao
 * @since 2019-12-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单信息表")
@TableName(value = "tb_order")
public class OrderPoJo extends BaseEntity {

  @ApiModelProperty(value = "顾客ID", example = "45115454")
  private Long customerId;

  @ApiModelProperty(value = "用户ID", example = "435677")
  private Long userId;

  @ApiModelProperty(value = "订单号", example = "123123123")
  private String orderNum;

  public static OrderPoJo of() {
    return new OrderPoJo();
  }

  public static OrderPoJo of(OrderReq req) {
    OrderPoJo poJo = OrderPoJo.of();
    // bean工具类，将一个实体对象属性赋值给另一个实体对象，这两个实体中的属性字段名称和字段类型必须保持一致，否则将拷贝不成功
    BeanUtils.copyProperties(req, poJo);
    return poJo;
  }
}
