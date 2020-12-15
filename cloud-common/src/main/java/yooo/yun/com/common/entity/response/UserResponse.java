package yooo.yun.com.common.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import yooo.yun.com.common.entity.BaseEntityResponse;
import yooo.yun.com.common.entity.pojo.user.UserPoJo;

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
@ApiModel("用户信息Response")
public class UserResponse extends BaseEntityResponse {

  @ApiModelProperty(value = "电话", example = "15675458912")
  private String tel;

  @ApiModelProperty(value = "用户头像", example = "http://test.jpg")
  private String avatar;

  @ApiModelProperty(value = "姓名", example = "周深")
  private String name;

  @ApiModelProperty(value = "账号状态1：正常，2：停用", example = "1")
  private Integer status;

  public static UserResponse of() {
    return new UserResponse();
  }

  public static UserResponse of(UserPoJo poJo) {
    UserResponse response = UserResponse.of();
    BeanUtils.copyProperties(poJo, response);
    return response;
  }
}
