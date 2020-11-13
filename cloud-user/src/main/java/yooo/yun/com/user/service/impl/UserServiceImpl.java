package yooo.yun.com.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yooo.yun.com.common.entity.pojo.UserPoJo;
import yooo.yun.com.common.service.Impl.BaseServiceImpl;
import yooo.yun.com.user.mapper.UserMapper;
import yooo.yun.com.user.service.UserService;

/**
 * @author WangJiao
 * @since 2020/11/12
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserPoJo> implements UserService {}
