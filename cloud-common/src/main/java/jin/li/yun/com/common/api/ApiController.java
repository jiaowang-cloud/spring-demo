package jin.li.yun.com.common.api;

import lombok.extern.slf4j.Slf4j;

/**
 * ApiResultEST API 公共控制器
 *
 * @author wangjiao
 * @since 2020/11/14
 */
@Slf4j
public class ApiController {

    /**
     * <p>
     * 请求成功
     * </p>
     *
     * @param data 数据内容
     * @param <T>  对象泛型
     * @return
     */
    protected <T> ApiResult ok(T data) {
        return ApiResult.ok(data);
    }

    /**
     * <p>
     * 请求失败
     * </p>
     *
     * @param msg 提示内容
     * @return
     */
    protected ApiResult fail(String msg) {
        return ApiResult.fail(msg);
    }

    /**
     * <p>
     * 请求失败
     * </p>
     *
     * @param apiCode 请求错误码
     * @return
     */
    protected ApiResult fail(ApiCode apiCode) {
        return ApiResult.fail(apiCode);
    }

}
