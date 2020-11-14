package yooo.yun.com.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 * @author liuhuan
 * @since 2020/2/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SpringBootPlusException extends RuntimeException{
    private Integer errorCode;
    private String message;

    public SpringBootPlusException(String message) {
        super(message);
        this.message = message;
    }

    public SpringBootPlusException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
