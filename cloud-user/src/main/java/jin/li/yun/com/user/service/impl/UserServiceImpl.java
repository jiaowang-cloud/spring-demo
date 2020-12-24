package jin.li.yun.com.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jin.li.yun.com.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jin.li.yun.com.common.entity.enums.UserRoleEnum;
import jin.li.yun.com.common.entity.pojo.user.UserPoJo;
import jin.li.yun.com.common.service.Impl.BaseServiceImpl;
import jin.li.yun.com.common.utils.JWTUtil;
import jin.li.yun.com.user.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * @author WangJiao
 * @since 2020/11/12
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserPoJo> implements UserService {
  @Resource private UserMapper mapper;

  @Override
  public UserPoJo getByTel(String tel) {
    log.info("getByTel:[tel:{}]", tel);
    return mapper.selectOne(Wrappers.<UserPoJo>lambdaQuery().eq(UserPoJo::getTel, tel));
  }

  @Override
  public int testE(int status) {
    int num = 0;
    return status / num;
  }

  @Override
  public String login(UserPoJo findUser, String loginType, String openId) {
    // TODO: 2020/11/25/025 其他业务逻辑处理

    String token =
        JWTUtil.generateSaasToken(findUser.getId(), UserRoleEnum.ADMIN.getValue(), null, loginType);
    log.info("login:[token:{}]", token);
    return token;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveUser(UserPoJo of) {
    boolean save = this.save(of);
    log.info("saveUser:[save:{}]", save);
    return save;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String loginMiNi(UserPoJo findUser, String loginType, String openId) {
    findUser.setOpenId(openId);
    this.updateById(findUser);
    String token =
        JWTUtil.generateSaasToken(
            findUser.getId(), UserRoleEnum.ADMIN.getValue(), openId, loginType);
    log.info("loginMiNi:[token:{}]", token);
    return token;
  }

  @Override
  public UserPoJo getByOpenId(String openId) {
    return mapper.selectOne(Wrappers.<UserPoJo>lambdaQuery().eq(UserPoJo::getOpenId, openId));
  }
}
