package jin.li.yun.com.common.api;

/**
 * REST API 响应码
 *
 * @author wangjiao
 * @since 2020/11/14
 */
public enum ApiCode {

  /** 系统级Code: 5000内 */
  SUCCESS(200, "操作成功"),
  UNAUTHORIZED(401, "非法访问"),
  NOT_PERMISSION(403, "没有权限"),
  NOT_FOUND(404, "你请求的资源不存在"),
  FAIL(500, "操作失败"),
  LOGIN_EXCEPTION(4000, "登陆失败"),
  SYSTEM_EXCEPTION(5000, "系统异常"),
  /** 参数校验级Code: 5001 - 6000 */
  PARAMETER_EXCEPTION(5001, "请求参数校验异常"),
  PARAMETER_PARSE_EXCEPTION(5002, "请求参数解析异常"),
  HTTP_MEDIA_TYPE_EXCEPTION(5003, "HTTP Media 类型异常"),
  SPRING_BOOT_PLUS_EXCEPTION(5100, "系统处理异常"),
  BUSINESS_EXCEPTION(5101, "业务处理异常"),
  DAO_EXCEPTION(5102, "数据库处理异常"),
  VERIFICATION_CODE_EXCEPTION(5103, "验证码校验异常"),
  AUTHENTICATION_EXCEPTION(5104, "登陆授权异常"),
  UNAUTHENTICATED_EXCEPTION(5105, "没有访问权限"),
  UNAUTHORIZED_EXCEPTION(5106, "没有访问权限"),
  SQL_ERROR_EXCEPTION(5107, "SQL语法异常"),
  DATA_NOT_EXIST(5108, "记录不存在"),
  TOKEN_HAS_EXPIRED(5109, "授权Token已失效，请重新授权"),
  /** 业务级Code: 6001 - 7000 */
  USER_UNAUTHORIZED(6001, "未授权，请先授权再访问"),
  USER_TWO_PASSWORDS_INCONSISTENT(6002, "两次输入密码不一致"),
  USER_ACCOUNT_REGISTERED(6003, "该账号已被注册"),
  USER_ACCOUNT_NOT_EXIST(6004, "该账号不存在，请先注册"),
  USER_PASSWORDS_ERROR(6005, "密码错误"),
  DATA_NOT_EXIST_FAILED_DELETE(6006, "数据不存在，删除失败"),
  ;
  private final int code;
  private String msg;

  ApiCode(final int code, final String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static ApiCode getApiCode(int code) {
   ApiCode[] ecs = ApiCode.values();
    for (ApiCode ec : ecs) {
      if (ec.getCode() == code) {
        return ec;
      }
    }
    return SUCCESS;
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
