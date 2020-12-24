package jin.li.yun.com.common.exception;

/**
 * 系统登录异常
 *
 * @author wangjiao
 * @since 2020/11/14
 */
public class SysLoginException extends SpringBootPlusException {

  public SysLoginException(String message) {
    super(message);
  }

  public SysLoginException(Integer errorCode, String message) {
    super(errorCode, message);
  }
}
