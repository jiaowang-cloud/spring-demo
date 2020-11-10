package yooo.yun.com.test;

import yooo.yun.com.user.pojo.UserPoJo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WangJiao
 * @since 2020/11/01
 */
public class TestMain {

  public static void main(String[] args) {
    List<UserPoJo> userList = new ArrayList();
    UserPoJo user = UserPoJo.of();
    user.setId(222L);
    user.setName("aaa");
    userList.add(user);

    UserPoJo user2 = UserPoJo.of();
    user2.setId(222L);
    user2.setName("aaa");
    userList.add(user2);

    UserPoJo user3 = UserPoJo.of();
    user3.setId(333L);
    user3.setName("aaa");
    userList.add(user3);

    UserPoJo user4 = UserPoJo.of();
    user4.setId(444L);
    user4.setName("aaa");
    userList.add(user4);

    testListToMap(userList);
  }

  public static void testListToMap(List<UserPoJo> userList) {

        Map<Long, UserPoJo> userIdMap =
            userList.stream().collect(Collectors.toMap(UserPoJo::getId, u -> u));

    Map<Long, String> userIdMap2 =
        userList.stream()
            .collect(Collectors.toMap(UserPoJo::getId, UserPoJo::getName, (k1, k2) -> k1));
    System.out.println("userIdMap: " + userIdMap);
  }
}
