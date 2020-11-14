package yooo.yun.com.user.service;

import yooo.yun.com.common.entity.pojo.UserPoJo;
import yooo.yun.com.common.service.BaseService;

/**
 * @author WangJiao
 * @since 2020/11/12
 */
public interface UserService extends BaseService<UserPoJo> {
  /**
   * query user info by tel
   *
   * @param tel the tel
   * @return user
   */
  UserPoJo getByTel(String tel);

  int testE(int status);
}