package yooo.yun.com.utils;

import org.springframework.util.AntPathMatcher;
import yooo.yun.com.provider.AuthProvider;

import java.util.Optional;
import java.util.stream.Stream;

public class UrlCheckUtil {

  /**
   * 默认放行和配置文件放行
   *
   * @param uri uri
   * @param white_url white_url
   * @return true/false
   */
  public static boolean isSkip(String uri, String white_url) {

    // 跳过白名单
    final AntPathMatcher matcher = new AntPathMatcher();

    return AuthProvider.getDefaultSkipUrl().stream()
            .map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT))
            .anyMatch(uri::contains)
        || AuthProvider.getWhiteUrl().stream()
            .map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT))
            .anyMatch(uri::equalsIgnoreCase)
        || Stream.of(Optional.ofNullable(white_url).orElse("").split(","))
            .anyMatch(pattern -> matcher.match(pattern, uri));
  }
}
