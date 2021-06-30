package jin.li.yun.com.producer.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jin.li.yun.com.common.entity.response.UserResponse;
import jin.li.yun.com.common.utils.StringUtils;
import org.springframework.amqp.rabbit.support.CorrelationData;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author WangJiao
 * @since 2021/05/21
 */
public class CacheUtils {
  private static final Map<String, String> SHOP_CACHE = new HashMap<>(1);

  public static String get(String id) {

    if (StringUtils.isNotEmpty(id)) {
      return SHOP_CACHE.get(id);
    }
    return null;
  }

  public static void put(String id) {
    if (StringUtils.isNotEmpty(id)) {
      SHOP_CACHE.put(id,id);
    }
  }

  public static void clear() {
    SHOP_CACHE.clear();
  }

  public static void main(String[] args) throws UnsupportedEncodingException {
    UserResponse build =
            UserResponse.builder()
                    .id(43251234L)
                    .name("小明")
                    .avatar("asfjdl")
                    .status(2)
                    .tel("15675458732")
                    .build();
    String string = JSON.toJSONString(build);
    System.out.println(string);
    System.out.println("aaaaa");
    byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
    System.out.println(bytes);
    String msg = new String(bytes, StandardCharsets.UTF_8);
    System.out.println(msg);
    System.out.println("bbbbbbbbbbb");
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("id",43251234L);
    jsonObject.put("name","小明");
    jsonObject.put("avatar","asfjdl");
    jsonObject.put("status",2);
    jsonObject.put("tel","15675458732");
    System.out.println(jsonObject.toJSONString());
    System.out.println("cccccccc");
    byte[] bytes3 = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
    System.out.println(bytes3);
    String msg3 = new String(bytes3, StandardCharsets.UTF_8);
    System.out.println(msg3);
  }
}


