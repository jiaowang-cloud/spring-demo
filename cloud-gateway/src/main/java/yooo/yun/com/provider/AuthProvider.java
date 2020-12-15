package yooo.yun.com.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * 身份验证提供者类
 *
 * @author WangJiao
 * @since 2020/11/10
 */
public class AuthProvider {
  public static String TARGET = "/**";
  public static String REPLACEMENT = "";
  // 过滤包含url
  private static List<String> defaultSkipUrl = new ArrayList<>();

  // 过滤全匹配
  private static List<String> whiteUrl = new ArrayList<>();

  static {
    defaultSkipUrl.add("/error/**");
    defaultSkipUrl.add("/assets/**");
    defaultSkipUrl.add("/v2/api-docs/**");
    defaultSkipUrl.add("/v2/api-docs-ext/**");
    defaultSkipUrl.add("/swagger-resources/configuration/ui");
    defaultSkipUrl.add("/swagger-resources");
    defaultSkipUrl.add("/swagger-resources/configuration/security");
    defaultSkipUrl.add("/swagger-ui.html");
    defaultSkipUrl.add("/webjars/**");

    // 外部访问
    // 白名单接口统一走 /模块名/outer/**
    defaultSkipUrl.add("/user/outer/**");
    defaultSkipUrl.add("/order/outer/**");
  }

  static {
    // 登录注册相关
    whiteUrl.add("/user/saas/user/register"); // 用户注册
    whiteUrl.add("/user/saas/user/login"); // 用户登录
    whiteUrl.add("/user/mini/user/auth-token"); // 用户认证
  }

  /** 默认无需鉴权的API */
  public static List<String> getDefaultSkipUrl() {
    return defaultSkipUrl;
  }
  /** 白名单API */
  public static List<String> getWhiteUrl() {
    return whiteUrl;
  }
}
