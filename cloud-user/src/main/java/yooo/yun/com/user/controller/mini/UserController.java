package yooo.yun.com.user.controller.mini;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import yooo.yun.com.common.api.ApiCode;
import yooo.yun.com.common.api.ApiResult;
import yooo.yun.com.common.entity.enums.LoginTypeEnum;
import yooo.yun.com.common.entity.pojo.user.UserPoJo;
import yooo.yun.com.common.entity.request.UserLoginReq;
import yooo.yun.com.common.entity.response.UserResponse;
import yooo.yun.com.common.utils.UUIDUtil;
import yooo.yun.com.user.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@Slf4j
@RequestMapping(value = "/mini/user")
@RestController(value = "miniUserC")
@Api("MINI 用户API")
public class UserController {
  @Resource private UserService service;

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
    String openId = UUIDUtil.getUUID(15);
    return ApiResult.ok(service.loginMiNi(findUser, LoginTypeEnum.MI_NI.getValue(), openId));
  }

  /**
   * 获取用户详情
   *
   * @param openId openId
   * @return res
   */
  @GetMapping("/detail")
  @ApiOperation("获取用户详情")
  public ApiResult detail(@RequestParam(value = "openId") String openId) {
    log.info("detail:[openId:{}]", openId);
    UserPoJo findUser = service.getByOpenId(openId);

    return Objects.nonNull(findUser)
        ? ApiResult.ok(UserResponse.of(findUser))
        : ApiResult.fail(ApiCode.USER_UNAUTHORIZED);
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
