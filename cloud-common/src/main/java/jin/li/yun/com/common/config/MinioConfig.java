package jin.li.yun.com.common.config;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author WangJiao
 * @since 2021/08/05
 */
@Configuration
@EnableConfigurationProperties(MinioProp.class)
public class MinioConfig {
  @Resource private MinioProp minioProp;

  /** 获取MinioClient */
  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder()
        .endpoint(minioProp.getEndpoint())
        .credentials(minioProp.getAccessKey(), minioProp.getSecretKey())
        .build();
//    MinioClient minioClient = null;
//    try {
//      minioClient = new MinioClient(minioProp.getEndpoint(), minioProp.getAccessKey(), minioProp.getSecretKey());
//    } catch (InvalidEndpointException e) {
//      e.printStackTrace();
//    } catch (InvalidPortException e) {
//      e.printStackTrace();
//    }
//    return minioClient;
  }
}
