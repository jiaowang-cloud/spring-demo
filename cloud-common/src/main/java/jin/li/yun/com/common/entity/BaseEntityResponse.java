package jin.li.yun.com.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类
 *
 * @author wangjiao
 * @since 2020/11/13
 */
@Data
@SuperBuilder
@ApiModel("BaseEntityRes")
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntityResponse implements Serializable {

  @TableId(value = "id")
  private Long id;

  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  private Date updateTime;
}
