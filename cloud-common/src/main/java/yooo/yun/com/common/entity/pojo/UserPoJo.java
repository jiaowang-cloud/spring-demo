package yooo.yun.com.common.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import yooo.yun.com.common.entity.BaseEntity;
import yooo.yun.com.common.entity.request.UserReq;

/**
 * 用户表
 *
 * @author WangJiao
 * @since 2019-12-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户表")
@TableName(value = "r_user")
public class UserPoJo extends BaseEntity {

  @ApiModelProperty(value = "电话", example = "15675458912")
  private String tel;

  @ApiModelProperty(value = "密码", example = "yyy23")
  private String password;

  @ApiModelProperty(value = "用户头像", example = "http://test.jpg")
  private String avatar;

  @ApiModelProperty(value = "姓名", example = "周深")
  private String name;

  @ApiModelProperty(value = "账号状态1：正常，2：停用", example = "1")
  private Integer status;

  public static UserPoJo of() {
    return new UserPoJo();
  }

  public static UserPoJo of(UserReq req) {
    UserPoJo poJo = UserPoJo.of();
    BeanUtils.copyProperties(req, poJo);
    return poJo;
  }
}
