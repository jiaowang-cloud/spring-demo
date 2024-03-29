package jin.li.yun.com.user.controller.saas;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jin.li.yun.com.common.entity.request.ContractTypeLabelUpdRequest;
import jin.li.yun.com.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import jin.li.yun.com.common.api.ApiCode;
import jin.li.yun.com.common.api.ApiResult;
import jin.li.yun.com.common.entity.enums.LoginTypeEnum;
import jin.li.yun.com.common.entity.pojo.user.UserPoJo;
import jin.li.yun.com.common.entity.request.UserLoginReq;
import jin.li.yun.com.common.entity.request.UserReq;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@Slf4j
@RequestMapping(value = "/saas/user")
@RestController(value = "sUserC")
@Api("SAAS 用户API")
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
    log.info("register:user register info[req:{}]", JSON.toJSONString(req));
    // 新增用户条件密码校验判断
    if (!Objects.equals(req.getPassword(), req.getRePassword())) {
      return ApiResult.fail(ApiCode.USER_TWO_PASSWORDS_INCONSISTENT);
    }
    // 手机号作为账号唯一性校验
    UserPoJo findUser = service.getByTel(req.getTel());
    if (Objects.nonNull(findUser)) {
      return ApiResult.fail(ApiCode.USER_ACCOUNT_REGISTERED);
    }

    // 密码进行md5加密
    req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
    boolean res = service.saveUser(UserPoJo.of(req));
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
    return ApiResult.ok(service.login(findUser, LoginTypeEnum.SAAS.getValue(), null));
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
    UserPoJo find = service.getById(id);
    if (Objects.isNull(find)) {
      return ApiResult.fail(ApiCode.DATA_NOT_EXIST_FAILED_DELETE);
    }
    return ApiResult.ok(service.removeById(id));
  }

}
