package jin.li.yun.com.neo4j.test;

import java.util.Arrays;

/**
 * @author WangJiao
 * @since 2021/07/16
 */
public enum ActionEnum {
  /** 状态：动作，枚举：删除.DELETE,添加.ADD,更新.UPDATE */
  DELETE("DELETE", "删除"),
  ADD("ADD", "添加"),
  UPDATE("UPDATE", "更新"),
  QUERY("QUERY", "查询"),
  ;

  private String code;
  private String desc;

  ActionEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String code() {
    return this.code;
  }

  public String desc() {
    return this.desc;
  }

  /**
   * 通过code获取desc
   *
   * @param code code
   * @return desc
   */
  public static String descOfCode(String code) {
    return Arrays.stream(values())
        .filter(it -> it.code().equals(code))
        .findFirst()
        .map(a -> a.desc)
        .orElse(null);
  }
}
