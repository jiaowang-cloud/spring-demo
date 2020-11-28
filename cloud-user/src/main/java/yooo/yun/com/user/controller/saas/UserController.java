package yooo.yun.com.user.controller.saas;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import yooo.yun.com.common.api.ApiCode;
import yooo.yun.com.common.api.ApiResult;
import yooo.yun.com.common.entity.enums.LoginTypeEnum;
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
    if (!Objects.equals(req.getPassword(), req.getRePassword())) {
      return ApiResult.fail(ApiCode.USER_TWO_PASSWORDS_INCONSISTENT);
    }
    UserPoJo findUser = service.getByTel(req.getTel());
    if (Objects.nonNull(findUser)) {
      return ApiResult.fail(ApiCode.USER_ACCOUNT_REGISTERED);
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
    String tel = req.getTel();
    UserPoJo findUser = service.getByTel(tel);
    if (Objects.isNull(findUser)) {
      return ApiResult.fail(ApiCode.USER_ACCOUNT_NOT_EXIST);
    }
    if (!Objects.equals(
        DigestUtils.md5DigestAsHex(req.getPassword().getBytes()), findUser.getPassword())) {
      return ApiResult.fail(ApiCode.USER_PASSWORDS_ERROR);
    }
    return ApiResult.ok(service.login(findUser, LoginTypeEnum.SAAS.getValue()));
  }

  /**
   * test
   *
   * @param status status
   * @return res
   */
  @PostMapping("/test")
  @ApiOperation("测试全局异常")
  public ApiResult testGlobalException(@RequestParam(value = "status") int status) {
    log.info("testGlobalException:[status:{}]", status);
    return ApiResult.ok(service.testE(status));
  }

  /**
   * test
   *
   * @param status status
   * @return res
   */
  @PostMapping("/test-api")
  @ApiOperation("测试api返回结果类型")
  public ApiResult testApiResult(@RequestParam(value = "status") int status) {
    log.info("testApiResult:[status:{}]", status);
    return Objects.equals(status, 1)
        ? ApiResult.ok(status)
        : ApiResult.fail(ApiCode.USER_ACCOUNT_REGISTERED);
  }

  /**
   * 获取用户详情
   *
   * @param id id
   * @return res
   */
  @GetMapping("/{id}")
  @ApiOperation("获取用户详情")
  public ApiResult detail(@PathVariable(value = "id") long id) {
    log.info("detail:[id:{}]", id);
    return ApiResult.ok(service.getById(id));
  }

  /**
   * 删除用户信息
   *
   * @param id id
   * @return res
   */
  @DeleteMapping("/delete/{id}")
  @ApiOperation("删除用户信息")
  public ApiResult delete(@PathVariable(value = "id") long id) {
    log.info("delete:[id:{}]", id);
    return ApiResult.ok(service.removeById(id));
  }
}
