package yooo.yun.com.user.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author WangJiao
 * @since 2019-12-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户表")
// @TableName(value = "tb_user")
public class UserPoJo implements Serializable {
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "ID", example = "43255")
  private Long id;

  @ApiModelProperty(value = "电话", example = "15675458912")
  private String tel;

  @ApiModelProperty(value = "密码", example = "yyy23")
  private String password;

  @ApiModelProperty(value = "用户头像", example = "http://test.jpg")
  private String avatar;

  @ApiModelProperty(value = "姓名", example = "周深")
  private String name;

  @ApiModelProperty(value = "key有效时间", example = "-")
  private Date keyOutTime;

  @ApiModelProperty(value = "账号状态1：正常，2：停用", example = "1")
  private Integer status;

  public static UserPoJo of() {
    return new UserPoJo();
  }
}
