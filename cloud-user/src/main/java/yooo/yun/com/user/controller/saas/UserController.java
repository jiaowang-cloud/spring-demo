package yooo.yun.com.user.controller.saas;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import yooo.yun.com.common.api.ApiResult;
import yooo.yun.com.common.entity.pojo.UserPoJo;
import yooo.yun.com.common.entity.request.UserLoginReq;
import yooo.yun.com.common.entity.request.UserReq;
import yooo.yun.com.user.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@Slf4j
@RequestMapping(value = "/saas/user")
@RestController("sUserC")
public class UserController {
  @Resource private UserService service;

  /**
   * 用户注册
   *
   * @param req 注册信息
   * @return obj
   */
  @PostMapping("/register")
  @ApiOperation("注册")
  public ApiResult register(@Valid @RequestBody UserReq req) {
    log.info("register:[req:{}]", JSON.toJSONString(req));
    if (Objects.equals(req.getPassword(), req.getRePassword())) {
      return ApiResult.fail("两次输入密码不一致");
    }
    UserPoJo findUser = service.getByTel(req.getTel());
    if (Objects.nonNull(findUser)) {
      return ApiResult.fail("该账号已被注册");
    }

    // md5加密
    req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
    boolean res = service.save(UserPoJo.of(req));
    log.info("register:[res:{}]", res);
    return ApiResult.ok(res);
  }

  /**
   * 用户登录
   *
   * @param req req
   * @return res
   */
  @PostMapping("/login")
  @ApiOperation("登录")
  public ApiResult login(@Valid @RequestBody UserLoginReq req) {
    log.info("login:[req:{}]", req);
    return ApiResult.ok();
  }

  /**
   * 用户登录
   *
   * @param status status
   * @return res
   */
  @PostMapping("/test")
  @ApiOperation("登录")
  public ApiResult testGlobalException(@RequestParam(value = "status") int status) {
    log.info("testGlobalException:[status:{}]", status);
    service.testE(status);
    return ApiResult.ok();
  }
}
