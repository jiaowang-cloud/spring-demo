package jin.li.yun.com.common.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户表
 *
 * @author WangJiao
 * @since 2019-12-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户登录信息请求")
public class UserLoginReq implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotBlank(message = "电话不能为空")
  @ApiModelProperty(value = "电话", example = "15675458912")
  private String tel;

  @NotBlank(message = "密码不能为空")
  @ApiModelProperty(value = "密码", example = "yyy23")
  private String password;

  public static UserLoginReq of() {
    return new UserLoginReq();
  }
}
