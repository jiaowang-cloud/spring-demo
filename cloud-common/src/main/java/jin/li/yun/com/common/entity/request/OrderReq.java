package jin.li.yun.com.common.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 订单信息请求
 *
 * @author WangJiao
 * @since 2021-01-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单信息请求")
public class OrderReq implements Serializable {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "顾客ID", example = "45115454")
  private Long customerId;

  @ApiModelProperty(value = "订单号", example = "123123123")
  private String orderNum;

  public static OrderReq of() {
    return new OrderReq();
  }
}
