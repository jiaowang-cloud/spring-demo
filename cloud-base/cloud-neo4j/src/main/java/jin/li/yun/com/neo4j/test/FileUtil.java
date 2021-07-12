package jin.li.yun.com.neo4j.test;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 当前工程目录下user.txt文件里有三行数据, <br>
 * 分别是:张三,123,李四,456,王五,789,编写程序读取文件里的数据,使用 HashMap集 合进行存储,并打印输出。
 * @author ThinkPad
 */
public class FileUtil {

  /**
   * 读取文件里的数据,使用 HashMap集 合进行存储
   *
   * @param file file
   * @return map
   */
  public static Map<String, String> readFileToMap(File file) {
    Map<String, String> map = new HashMap<>();
    String str;
    // try-with-resource关闭外部连接
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      // 判断最后一行不存在，为空结束循环
      while ((str = br.readLine()) != null) {
        // 原样输出读到的内容
        System.out.println(str);
        // 读出一行用英文状态下的逗号分隔开，取出编号和用户名存放到map
        String[] split = str.trim().split(",");
        map.put(split[1], split[0]);
      }
    } catch (IOException e) {
      // 异常需要进行处理
      e.printStackTrace();
    }
    return map;
  }

  public static void main(String[] args) throws FileNotFoundException {
    File file = ResourceUtils.getFile("classpath:user.txt");
    Map<String, String> map = readFileToMap(file);
    System.out.println("map: " + map);
  }
}
