package yooo.yun.com.common.exception;

/**
 * DAO异常
 *
 * @author wangjiao
 * @since 2020/11/14
 */
public class DaoException extends SpringBootPlusException {

  public DaoException(String message) {
    super(message);
  }

  public DaoException(Integer errorCode, String message) {
    super(errorCode, message);
  }
}
