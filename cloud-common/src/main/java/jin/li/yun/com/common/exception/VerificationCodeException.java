package jin.li.yun.com.common.exception;

/**
 * 验证码校验异常
 *
 * @author wangjiao
 * @since 2020/11/14
 */
public class VerificationCodeException extends SpringBootPlusException {

    public VerificationCodeException(String message) {
        super(message);
    }

    public VerificationCodeException(Integer errorCode, String message) {
        super(errorCode, message);
    }

}
