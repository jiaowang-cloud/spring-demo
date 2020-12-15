package yooo.yun.com.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/** */
@Slf4j
public class JWTUtil {
  /** 秘钥 */
  private static final String SECRET_KEY = "54d54365-5342-7643-5gf4-8743ac6426y7";
  /** token过期时间(单位：豪秒) */
  public static final long TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000;
  //  public static final long TOKEN_EXPIRE_TIME = 3000; // token过期时间
  /** 签发人 */
  private static final String ISSUER = "issuer";

  private static String generateToken(
      final long userId, final Integer role, final String openId, final String loginType) {
    Date now = new Date();
    // 算法HMAC256
    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    String token =
        JWT.create()
            // 签发人
            .withIssuer(ISSUER)
            // 签发时间
            .withIssuedAt(now)
            // 过期时间
            .withExpiresAt(new Date(now.getTime() + TOKEN_EXPIRE_TIME))
            .withClaim("role", role)
            .withClaim("userId", userId)
            .withClaim("openId", openId)
            .withClaim("loginType", loginType)
            // 签名算法HMAC256
            .sign(algorithm);
    log.info("generateToken: 生成的token为 [{}]", token);
    return token;
  }

  public static String generateSaasToken(
      final long userId, final Integer role, final String openId, final String loginType) {
    return generateToken(userId, role, openId, loginType);
  }
  /** 验证token是否合法 */
  public static boolean verify(String token) {
    try {
      log.info("verify:[验证token start]");
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); // 算法
      JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
      verifier.verify(token);
      return true;
    } catch (Exception e) {
      log.warn("verify:[{}]", e.getMessage());
      log.error(e.getMessage(), e);
      return false;
    }
  }

  public static void main(String[] args) {
    String token = generateToken(1, 1, null, "saas");
    log.info("main:[verify:{}]", verify(token));
  }

  /** 从token获取访问类型 */
  public static String getLoginType(String token) {
    try {
      return JWT.decode(token).getClaim("loginType").asString();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  /** 从token获取userId */
  public static Long getUserId(String token) {
    try {
      return JWT.decode(token).getClaim("userId").asLong();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  /** 从token获取Role */
  public static Integer getRole(String token) {
    try {
      return JWT.decode(token).getClaim("role").asInt();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  /** 从token获取username */
  public static String getOpenId(String token) {
    try {
      return JWT.decode(token).getClaim("openId").asString();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static TokenEntity getTokenEntity(final String token) {
    TokenEntity tokenEntity = new TokenEntity();
    tokenEntity.setLoginType(getLoginType(token));
    tokenEntity.setRole(getRole(token));
    tokenEntity.setUserId(getUserId(token));
    tokenEntity.setVerified(verify(token));
    tokenEntity.setOpenId(getOpenId(token));
    return tokenEntity;
  }

  @Data
  @ToString
  public static class TokenEntity {
    /** {@link LoginTypeEnum} */
    private String loginType;

    /** 角色 */
    private Integer role;

    /** 用户ID */
    private Long userId;

    /** token验证true：验证成功，false:验证失败 */
    private boolean verified;

    /** 迷你端登录openId */
    private String openId;
  }

  @AllArgsConstructor
  public enum Role {
    /** 管理员. */
    ADMIN(1, "管理员"),
    /** 普通员工,不能登录saas后台 */
    STAFF(2, "员工");

    @Getter private int value;
    @Getter private String desc;
  }

  @AllArgsConstructor
  public enum LoginTypeEnum {

    /** 三个端登录方式 */
    SAAS("saas", "saas端登录"),
    MI_NI("mini", "mini登录");

    @Getter private String value;
    @Getter private String desc;
  }
}
