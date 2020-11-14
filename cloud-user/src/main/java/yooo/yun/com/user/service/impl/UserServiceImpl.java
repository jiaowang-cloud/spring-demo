package yooo.yun.com.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yooo.yun.com.common.entity.pojo.UserPoJo;
import yooo.yun.com.common.service.Impl.BaseServiceImpl;
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
    int res = status / num;
    return res;
  }
}
