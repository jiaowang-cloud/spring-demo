package yooo.yun.com.user.service;

import yooo.yun.com.common.api.ApiCode;
import yooo.yun.com.common.entity.pojo.user.UserPoJo;
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

  /**
   * 登录
   *
   * @param findUser findUser
   * @param loginType loginType
   * @param openId openId
   * @return token
   */
  String login(UserPoJo findUser, String loginType, String openId);

  /**
   * save userInfo
   *
   * @param of user
   * @return true
   */
  boolean saveUser(UserPoJo of);

  /**
   * mini端登录
   *
   * @param findUser findUser
   * @param value value
   * @param openId openId
   * @return token
   */
  String loginMiNi(UserPoJo findUser, String value, String openId);

  /**
   * query by openId
   *
   * @param openId openId
   * @return user
   */
  UserPoJo getByOpenId(String openId);
}
