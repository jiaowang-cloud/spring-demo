package jin.li.yun.com.common.entity.pojo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import jin.li.yun.com.common.entity.BaseEntity;
import jin.li.yun.com.common.entity.request.UserReq;

/**
 * 用户信息
 *
 * @author WangJiao
 * @since 2019-12-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户表")
@TableName(value = "tb_user")
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

  @ApiModelProperty(value = "openId", example = "3465555fa555345555ert6774df")
  private String openId;

  public static UserPoJo of() {
    return new UserPoJo();
  }

  public static UserPoJo of(UserReq req) {
    UserPoJo poJo = UserPoJo.of();
    // bean工具类，将一个实体对象属性赋值给另一个实体对象，这两个实体中的属性字段名称和字段类型必须保持一致，否则将拷贝不成功
    BeanUtils.copyProperties(req, poJo);
    return poJo;
  }
}
