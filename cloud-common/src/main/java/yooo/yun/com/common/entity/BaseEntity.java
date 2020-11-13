package yooo.yun.com.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel("BaseEntity")
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @ApiModelProperty(value = "创建时间")
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  private Date createTime;

  @ApiModelProperty(value = "更新时间")
  @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

  @ApiModelProperty(value = "逻辑未删除:0,逻辑已删除:1")
  private Boolean isDelete;
}
