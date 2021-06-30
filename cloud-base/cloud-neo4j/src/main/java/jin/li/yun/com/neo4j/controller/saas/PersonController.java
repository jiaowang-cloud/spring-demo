package jin.li.yun.com.neo4j.controller.saas;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jin.li.yun.com.common.api.ApiCode;
import jin.li.yun.com.common.api.ApiResult;
import jin.li.yun.com.common.entity.enums.LoginTypeEnum;
import jin.li.yun.com.common.entity.pojo.user.UserPoJo;
import jin.li.yun.com.common.entity.request.UserLoginReq;
import jin.li.yun.com.common.entity.request.UserReq;
import jin.li.yun.com.neo4j.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@Slf4j
@RequestMapping(value = "/saas/person")
@RestController(value = "sPersonC")
@Api("SAAS Person API")
public class PersonController {
  @Resource private PersonService service;

  /**
   * 获取用户详情
   *
   * @return res
   */
  @GetMapping("/list")
  @ApiOperation("获取用户详情")
  public ApiResult list() {
    return ApiResult.ok(service.findAll());
  }

  /**
   * 获取用户详情
   *
   * @return res
   */
  @GetMapping("/list/name")
  @ApiOperation("根据名称获取用户详情")
  public ApiResult listByName(@RequestParam(value = "name") String name) {
    return ApiResult.ok(service.findByName(name));
  }
}
