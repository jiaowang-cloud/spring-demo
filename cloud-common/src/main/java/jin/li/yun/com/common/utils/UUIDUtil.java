package jin.li.yun.com.common.utils;

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

  public static void main(String[] args) {
    getNum2();
  }

  /**
   * 求1000以内含3的自然数的个数
   *
   * @return num
   */
  public static int getNum2() {
    int count = 0;
    int temp = 1;
    for (int i = 0; i < 1000; i++) {
      if (String.valueOf(i).contains("3")) {
        System.out.print(i + " ");
        temp++;
        if (temp % 20 == 0) {
          System.out.println();
        }
        count++;
      }
    }
    System.out.println();
    System.out.println("count: " + count);
    return count;
  }

  /**
   * 求1000以内含3的自然数的个数
   *
   * @return num
   */
  public static int getNum() {
    int a, b, x, y, z, cont1 = 0, cont2 = 0;
    for (int i = 0; i < 100; i++) {
      a = i / 10;
      b = i % 10;
      if (a == 3 || b == 3) {
        System.out.print(i + " ");
        cont1++;
      }
    }
    System.out.println("cont1: " + cont1);
    for (int i = 100; i <= 999; i++) {
      x = i / 100;
      y = i / 10 % 10;
      z = i % 10;
      if (x == 3 || y == 3 || z == 3) {
        cont2++;
        System.out.print(i + " ");
      }
    }
    System.out.println("cont2: " + cont2);
    return cont1 + cont2;
  }
}
