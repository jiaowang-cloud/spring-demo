package jin.li.yun.com.common.entity.response.minio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangJiao
 * @since 2021/08/05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {
  private String urlHttp;

  private String urlPath;
}
