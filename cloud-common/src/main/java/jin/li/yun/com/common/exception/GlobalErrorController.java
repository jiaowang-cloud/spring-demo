package jin.li.yun.com.common.exception;

import jin.li.yun.com.common.api.ApiCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import jin.li.yun.com.common.api.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局Error/404处理
 *
 * @author wangjiao
 * @since 2020/11/14
 */
@ApiIgnore
@RestController
@Slf4j
public class GlobalErrorController implements ErrorController {

  private static final String ERROR_PATH = "/error";

  @RequestMapping(ERROR_PATH)
  public ApiResult handleError(HttpServletRequest request, HttpServletResponse response) {
    int status = response.getStatus();
    log.info("handleError:response[status:{}]",status);
    switch (status) {
      case HttpServletResponse.SC_UNAUTHORIZED:
        log.error("Unauthorized");
        return ApiResult.fail(ApiCode.UNAUTHORIZED);
      case HttpServletResponse.SC_FORBIDDEN:
        log.error("Permission denied");
        return ApiResult.fail(ApiCode.NOT_PERMISSION);
      case HttpServletResponse.SC_NOT_FOUND:
        log.error("404 NOT FOUND");
        return ApiResult.fail(ApiCode.NOT_FOUND);
      default:
        log.error("ERROR...");
        break;
    }
    return ApiResult.fail(ApiCode.FAIL);
  }

  @Override
  public String getErrorPath() {
    log.error("errorPath....");
    return ERROR_PATH;
  }
}
