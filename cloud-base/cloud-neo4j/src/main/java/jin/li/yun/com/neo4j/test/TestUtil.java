package jin.li.yun.com.neo4j.test;

import java.util.Arrays;

/**
 * @author WangJiao
 * @since 2021/07/07
 */
public class TestUtil {

  public static void main(String[] args) {
    System.out.println("ActionEnum.values:" + Arrays.toString(ActionEnum.values()));
    System.out.println("ActionEnum根据code获取desc: " + ActionEnum.descOfCode("ADD"));

  }
}
