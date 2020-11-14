package yooo.yun.com.common.api;

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
  SMS_NOT_RECHARGE(5200, "短信未充值"),
  SMS_QUANTITY_NOT_ENOUGH(5201, "短信余量不足"),
  /** 业务级Code: 6001 - 7000 */
  THIS_TYPE_AWARD_NOT_EXIST(6001, "该类型活动不存在"),
  CUSTOMER_NOT_EXIST(6002, "查询不到当前客户"),
  AWARD_NOT_EXIST(6003, "该奖品不存在"),
  AWARD_ENERGY_NOT_ENOUGH(6004, "该奖品助力值尚未达标"),
  AWARD_OPEN_TIME_NOT_ENOUGH(6005, "该奖品开奖时间尚未达标"),
  ALREADY_LUCK_DRAW(6006, "您已参加过抽奖"),
  TASK_MAX_FREQUENCY(6007, "任务已达上限"),
  TASK_NOT_BEGIN_ERROR(6008, "任务还未开始"),
  TASK_SECRET_CODE_ERROR(6009, "暗号有误,请重新输入"),
  CUSTOMER_INFO_ERROR(6010, "完善资料有误,请重新输入"),
  TASK_COMPLETE_MATERIAL_ERROR(6011, "完善失败,请重试"),
  Not_SHOP_ERROR(6012, "还未购买心仪的宝贝"),

  USER_UNAUTHORIZED(7000, "未授权，请先授权再访问");
  private final int code;
  private final String msg;

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
}
