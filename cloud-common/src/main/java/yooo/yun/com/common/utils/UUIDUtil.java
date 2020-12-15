package yooo.yun.com.common.utils;

import java.util.UUID;

/**
 * @author wangjiao
 * @since 2020/12/15
 */
public class UUIDUtil {

  public static String getUUID() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  public static String getUUID(int num) {
    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    return num < uuid.length() ? uuid.substring(0, num) : uuid;
  }
}
