package yooo.yun.com.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yooo.yun.com.common.entity.enums.UserRoleEnum;
import yooo.yun.com.common.entity.pojo.user.UserPoJo;
import yooo.yun.com.common.service.Impl.BaseServiceImpl;
import yooo.yun.com.common.utils.JWTUtil;
import yooo.yun.com.user.mapper.UserMapper;
import yooo.yun.com.user.service.UserService;

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
  public String login(UserPoJo findUser, String loginType) {
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
}
