package yooo.yun.com.common.entity.pojo.order;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import yooo.yun.com.common.entity.BaseEntity;

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
}
