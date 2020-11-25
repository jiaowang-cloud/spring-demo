package yooo.yun.com.common.entity.enums;

import lombok.Getter;

/**
 * 登录方式
 *
 * @author wangjiao
 * @since 2020/11/25
 */
public enum UserRoleEnum {

  ADMIN(1, "admin"),
  STAFF(2, "staff"),;

  @Getter private Integer value;
  @Getter private String desc;

  UserRoleEnum(Integer value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  public Integer value() {
    return this.value;
  }

  public String desc() {
    return this.desc;
  }
}
