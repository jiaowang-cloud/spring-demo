package yooo.yun.com.user.controller.saas;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import yooo.yun.com.common.entity.request.UserLoginReq;
import yooo.yun.com.common.entity.request.UserReq;
import yooo.yun.com.user.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@Slf4j
@RequestMapping(value = "/saas/user")
@RestController("userC")
public class UserController {
  @Resource private UserService userService;

  @GetMapping("/{id}")
  public String detail(@PathVariable("id") long id) throws JSONException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("id", id);
    jsonObject.put("msg", "user 模块");
    return jsonObject.toString();
  }

  @PostMapping("/register")
  @ApiOperation("注册")
  public String register(@Valid @RequestBody UserReq req) throws JSONException {
    log.info("register:[req:{}]", JSON.toJSONString(req));
    JSONObject jsonObject = new JSONObject();

    jsonObject.put("msg", "user 模块");
    return jsonObject.toString();
  }

  @PostMapping("/login")
  @ApiOperation("登录")
  public String login(@Valid @RequestBody UserLoginReq req) throws JSONException {
    log.info("login:[req:{}]", JSON.toJSONString(req));

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("msg", "user 模块");
    return jsonObject.toString();
  }
}
