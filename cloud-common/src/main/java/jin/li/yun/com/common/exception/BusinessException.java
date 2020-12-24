package jin.li.yun.com.common.exception;

/**
 * 业务异常
 *
 * @author wangjiao
 * @since 2020/11/14
 */
public class BusinessException extends SpringBootPlusException {

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(Integer errorCode, String message) {
    super(errorCode, message);
  }
}
