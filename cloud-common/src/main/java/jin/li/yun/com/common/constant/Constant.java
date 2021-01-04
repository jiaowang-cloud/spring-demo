package jin.li.yun.com.common.constant;

import jin.li.yun.com.common.entity.enums.LoginTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统常量定义.
 *
 * @author wangjiao
 * @since 2020/11/25
 */
@SuppressWarnings({"unused"})
@Slf4j
public class Constant {

  /** The constant HTTPS_PREFIX. */
  public static final String HTTPS_PREFIX = "https://";

  /** The constant HTTP_PREFIX. */
  public static final String HTTP_PREFIX = "http://";

  /** 系统默认ID（用于有时新增） * */
  public static final long SYSTEM_ID = 1L;
  /** 工作台上传图片(OSS文件夹ID) * */
  public static final long OSS_WORKBENCH = 2L;
  /** 二维码上传图片(OSS文件夹ID) * */
  public static final long OSS_PAY_QR_CODE = 3L;

  /** 默认list字符串分隔符 */
  public static final String DEFAULT_STRING_SPLIT = ",";

  /** 默认list字符串以空格为分割符 */
  public static final String DEFAULT_SPLIT_SPACE = " ";

  /** The constant DEFAULT_STRING_SHORT_LINE. */
  public static final String DEFAULT_STRING_SHORT_LINE = "-";

  /** The constant DEFAULT_STRING_BLANK. */
  public static final String DEFAULT_STRING_BLANK = "";

  /** The constant DEFAULT_STRING_DOT. */
  public static final String DEFAULT_STRING_DOT = ".";

  /** The constant DEFAULT_STRING_SEMICOLON. */
  public static final String DEFAULT_STRING_SEMICOLON = ";";

  /** The constant DEFAULT_STRING_COLON. */
  public static final String DEFAULT_STRING_COLON = ":";

  /** The constant DEFAULT_STRING_ASTERISK. */
  public static final String DEFAULT_STRING_ASTERISK = "*";

  /** The constant DEFAULT_STRING_EQUAL_SIGN. */
  public static final String DEFAULT_STRING_EQUAL_SIGN = "=";

  /** The constant DEFAULT_CHARSET_ENCODE. */
  public static final String DEFAULT_CHARSET_ENCODE = "UTF-8";
  /** 分页每页默认条数 */
  public static final int DEFAULT_PAGE_SIZE = 10;
  /** 默认时区 */
  public static final ZoneOffset DEFAULT_ZONE = ZoneOffset.of("+8");

  /** The constant BOSS. */
  public static final String BOSS = "BOSS";

  /** The constant PRODUCT. */
  public static final String PRODUCT = "PRODUCT";

  /** The constant SKU. */
  public static final String SKU = "SKU";
  /** 最小数量 */
  public static final int MIN_NUM = 1;

  /** The constant MIN_MONEY. */
  public static final int MIN_MONEY = 1;
  /** 全部分类 */
  public static final Long ALL_CLASS_ID = 0L;
  /** 无效id */
  public static final Long INVALID_ID = 0L;

  /** The constant WX_SUCCESS. */
  public static final String WX_SUCCESS = "SUCCESS";

  /** The constant WX_FAIL. */
  public static final String WX_FAIL = "FAIL";

  /** The constant WX_OK. */
  public static final String WX_OK = "OK";

  /** The constant WX_USER_PAYING. */
  public static final String WX_USER_PAYING = "USERPAYING";

  /** The constant IS_EXIST. */
  public static final String IS_EXIST = "isExist";

  /** The constant IS_EXIST. */
  public static final String RESULT = "result";

  /** The constant COMMON. */
  public static final String COMMON = "common";

  /** The constant OPENAPI. */
  public static final String OPENAPI = "openapi";

  /** The constant CREATE_TIME. */
  public static final String CREATE_TIME = "create_time";

  /** The constant UPDATE_TIME. */
  public static final String UPDATE_TIME = "update_time";

  /** The constant INT_ZERO. */
  public static final int INT_ZERO = 0;

  /** The constant LONG_ZERO. */
  public static final Long LONG_ZERO = 0L;

  /** The constant LONG_ONE. */
  public static final Long LONG_ONE = 1L;

  /** The constant BASE_REBATE. */
  public static final Integer BASE_REBATE = 100;

  /** The constant ORDER_CLOSE_TIME_DAY. */
  public static final Long ORDER_CLOSE_TIME_DAY = 1L;

  /** The constant ORDER_FINISH_TIME_DAY. */
  public static final Long ORDER_FINISH_TIME_DAY = 7L;

  /** The constant PPT_DOT. */
  public static final String PPT_DOT = ".ppt";

  /** The constant PPTX_DOT. */
  public static final String PPTX_DOT = ".pptx";

  /** The constant PDF_DOT. */
  public static final String PDF_DOT = ".pdf";

  /** The constant TTF_DOT. */
  public static final String TTF_DOT = ".ttf";

  /** The constant OTF_DOT. */
  public static final String OTF_DOT = ".otf";

  /** The constant MP4_DOT. */
  public static final String MP4_DOT = ".mp4";

  /** The constant PNG_DOT. */
  public static final String PNG_DOT = ".png";

  /** The constant JPG_DOT. */
  public static final String JPG_DOT = ".jpg";

  /** The constant JPG_DOT. */
  public static final String JPEG_DOT = ".jpeg";

  /** The constant PNG. */
  public static final String PNG = "PNG";

  /** The constant DEFAULT_AVATAR. */
  public static final String DEFAULT_AVATAR =
      "https://rateloss.oss-cn-chengdu.aliyuncs.com/416605082186016/20200312114145970.jpg";

  /** The constant DEFAULT_COMPANY_LOGO. */
  public static final String DEFAULT_COMPANY_LOGO =
      "https://riceoss.oss-cn-zhangjiakou.aliyuncs.com/1598619492511423/20200925144842697.jpg";

  /** 校验名称长度 */
  public static final int NAME_LENGTH_FIFTY = 50;

  /** WX重调 */
  public static final String WX_RECALL = "Y";

  /** base64前缀 */
  public static final String BASE64_PREFIX = "data:image/png;base64,";

  /** 设备操作常量 */
  public static class DeviceOperation {
    /** 首页 */
    public static final String HOME = "home";
    /** 详情 */
    public static final String DETAIL = "detail";
    /** 品类 */
    public static final String GROUP = "group";
    /** 品牌 */
    public static final String BRAND = "brand";
    /** 购买 */
    public static final String BUY = "buy";
    /** action 操作 */
    public static final String RFID = "RFID";
  }

  /** 请求头key */
  public static class HeaderKey {
    /** The constant ROLE. */
    public static final String ROLE = "role";

    /** The constant USER_ID. */
    public static final String USER_ID = "userId";

    /** The constant AUTHORIZATION. */
    public static final String AUTHORIZATION = "Authorization";

    /** The constant CUSTOMER_ID. */
    public static final String CUSTOMER_ID = "customerId";

    /** The constant UPDATE_ID. */
    public static final String UPDATE_ID = "updateId";

    /** The constant OPEN_ID. */
    public static final String OPEN_ID = "openId";
  }

  /** 请求头key */
  public static class ServerName {
    /** The constant CLOUD_ORDER. */
    public static final String CLOUD_ORDER = "cloud-order";

    /** The constant CLOUD_USER. */
    public static final String CLOUD_USER = "cloud-user";

    /** The constant NAME_MAP. */
    public static Map<String, String> NAME_MAP = new HashMap<>();

    static {
      NAME_MAP.put(CLOUD_ORDER, "订单中心");
      NAME_MAP.put(CLOUD_USER, "用户中心");
    }
  }

  /**
   * Gets token key.
   *
   * @param userId the user id
   * @param loginType the login type
   * @return the token key
   */
  public static String getTokenKey(Long userId, String loginType) {
    return "rice:token:" + loginType + ":" + userId;
  }

  /**
   * Gets w xmall token key.
   *
   * @param employeeId the employee id
   * @param customerId the customer id
   * @return the w xmall token key
   */
  public static String getMiNiTokenKey(Long employeeId, Long customerId) {
    log.info(
        " RedisKey.getWXmallTokenKey : [TokenKey:{}]",
        "rice:token:" + LoginTypeEnum.MI_NI.getValue() + ":" + employeeId + ":" + customerId);
    return "rice:token:" + LoginTypeEnum.MI_NI.getValue() + ":" + employeeId + ":" + customerId;
  }

  /** 订单状态的过期时间 */
  public static Long ORDER_STATUS_EXPIRE_TIME = 60L;

  /**
   * Gets order key.
   *
   * @param orderId the order id
   * @return the order key
   */
  public static String getOrderKey(Long orderId) {
    return "rice:order:pay:" + orderId;
  }

  /**
   * Gets warning order key.
   *
   * @return the warning order key
   */
  public static String getWarningOrderKey() {
    return "rice:order:warning:" + "Ids";
  }

  /**
   * Gets wx access token key.
   *
   * @param appId the app id
   * @return the wx access token key
   */
  public static String getWXAccessTokenKey(String appId) {
    return "rice:ma:accesstoken:" + appId;
  }

  /**
   * Gets geo key.
   *
   * @param companyId the company id
   * @return the geo key
   */
  public static String getGeoKey(Long companyId) {
    return "rice:geo:" + companyId;
  }

  /**
   * 客户标签id
   *
   * @param tagId 顾客标签ID
   * @return 顾客标签 customer tag key
   */
  public static String getCustomerTagKey(Long tagId) {
    return "rice:customer:tag:" + tagId;
  }

  /**
   * Gets employee report key.
   *
   * @param employeeId the employee id
   * @return the employee report key
   */
  public static String getEmployeeReportKey(Long employeeId) {
    return "rice:datacenter:report:" + employeeId + LocalDate.now();
  }

  /** The constant CUSTOMER_USER_ID_SEQ. */
  public static String CUSTOMER_USER_ID_SEQ = "rice:customer:tag:CUSTOMER_USER_ID_SEQ";

  /**
   * Get string.
   *
   * @param integer the integer
   * @return the string
   */
  public static String get(Integer integer) {
    return "rice:customer:tag:userid" + integer;
  }

  /**
   * redis key 过期的频道<br>
   * 需要开启过期通知功能 notify-keyspace-events Ex 打开, notify-keyspace-events "" 注释<br>
   * config set notify-keyspace-events Ex <br>
   * *代表所有的库,可以指定1之类的
   */
  public static final String REDIS_KEY_EVENT_EXPIRED = "__keyevent@*__:expired";
  /** 完成订单前缀 */
  public static final String ORDER_FINISH_PREFIX = "rice:order:finish:";
  /** 完成订单延期时间 */
  public static final long ORDER_FINISH_DALEY_TIME = 7 * 24 * 60 * 60;
  /** 关闭订单前缀 */
  public static final String ORDER_CLOSE_PREFIX = "rice:order:close:";
  /** 关闭订单延期时间 */
  public static final long ORDER_CLOSE_DALEY_TIME = 24 * 60 * 60;

  /** 关闭活动礼包前缀 */
  public static final String ACTIVITY_GIFT_END_PREFIX = "rice:activity:gift:end:";
  /** 开始活动礼包前缀 */
  public static final String ACTIVITY_GIFT_START_PREFIX = "rice:activity:gift:start:";
  /** 关闭活动礼包前缀 */
  public static final String ACTIVITY_COUPON_END_PREFIX = "rice:activity:coupon:end:";
  /** 开始活动礼包前缀 */
  public static final String ACTIVITY_COUPON_START_PREFIX = "rice:activity:coupon:start:";
  /** 优惠券过期前缀 */
  public static final String ACTIVITY_CUSTOMER_COUPON_EXPIRE_PREFIX =
      "rice:activity:customer:coupon:expire:";

  public static final String COMPANY_PREFIX = "rice:company:";
  public static final String GEO_PREFIX = "rice:geo:";
  /**
   * 去除前缀 获取真实id
   *
   * @param key key
   * @param prefix prefix
   * @return Long id
   */
  public static Long getId(final String key, final String prefix) {
    return Long.parseLong(key.replace(prefix, "").trim());
  }

  /**
   * Gets payment template msg key.
   *
   * @param tel the tel
   * @return the payment template msg key
   */
  public static String getPaymentTemplateMsgKey(String tel) {
    return "rice:order:payment-template:validate-code".concat(tel);
  }
}
