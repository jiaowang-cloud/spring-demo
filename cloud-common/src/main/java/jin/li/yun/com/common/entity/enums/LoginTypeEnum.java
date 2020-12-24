package jin.li.yun.com.common.entity.enums;

import lombok.Getter;

/**
 * 登录方式
 *
 * @author wangjiao
 * @since 2020/11/25
 */
public enum LoginTypeEnum {

  /** 三个端登录方式 */
  SAAS("saas", "saas端登录"),
  MI_NI("mini", "mini登录"),;

  @Getter private String value;
  @Getter private String desc;

  LoginTypeEnum(String value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  public String value() {
    return this.value;
  }

  public String desc() {
    return this.desc;
  }
}
