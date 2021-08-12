package jin.li.yun.com.material.controller.saas;

import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jin.li.yun.com.common.api.ApiResult;
import jin.li.yun.com.common.entity.response.minio.FileUploadResponse;
import jin.li.yun.com.common.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author WangJiao
 * @since 2021/08/05
 */
@Slf4j
@RestController
@RequestMapping("/saas/minio")
@Api("SAAS MINIO")
public class MinioController {
  @Resource private MinioUtil minioUtil;

  /** 上传文件 */
  @PostMapping("/buckets")
  @ApiOperation("上传文件")
  public ApiResult get() {
    List<Bucket> allBuckets = null;
    try {
      allBuckets = minioUtil.getAllBuckets();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ApiResult.ok(allBuckets);
  }

  /** 上传文件 */
  @PostMapping("/upload")
  @ApiOperation("上传文件")
  public ApiResult upload(@RequestParam(name = "file") MultipartFile file) {
    FileUploadResponse response = null;
    try {
      response = minioUtil.uploadFile(file);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("上传失败 : [{}]", e.getMessage());
      return ApiResult.fail("上传失败");
    }
    return ApiResult.ok(response);
  }

  /** 删除文件 */
  @DeleteMapping("/delete/{objectName}")
  public ApiResult delete(@PathVariable("objectName") String objectName) throws Exception {
    minioUtil.removeObject(objectName);
    log.info("delete:[objectName:{}]", objectName);
    return ApiResult.ok();
  }

  /** 下载文件到本地 */
  @GetMapping("/download/{objectName}")
  public ResponseEntity<byte[]> downloadToLocal(
      @PathVariable("objectName") String objectName, HttpServletResponse response)
      throws Exception {
    ResponseEntity<byte[]> responseEntity = null;
    InputStream stream = null;
    ByteArrayOutputStream output = null;
    try {
      // 获取"myobject"的输入流。
      stream = minioUtil.getObject(objectName);
      if (stream == null) {
        System.out.println("文件不存在");
        log.error("downloadToLocal:[文件不存在]");
      }
      // 用于转换byte
      output = new ByteArrayOutputStream();
      byte[] buffer = new byte[4096];
      int n = 0;
      while (-1 != (n = stream.read(buffer))) {
        output.write(buffer, 0, n);
      }
      byte[] bytes = output.toByteArray();

      // 设置header
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add("Accept-Ranges", "bytes");
      httpHeaders.add("Content-Length", bytes.length + "");
      //            objectName = new String(objectName.getBytes("UTF-8"), "ISO8859-1");
      // 把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
      httpHeaders.add("Content-disposition", "attachment; filename=" + objectName);
      httpHeaders.add("Content-Type", "text/plain;charset=utf-8");
      //            httpHeaders.add("Content-Type", "image/jpeg");
      responseEntity = new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.CREATED);

    } catch (MinioException e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        stream.close();
      }
      if (output != null) {
        output.close();
      }
    }
    return responseEntity;
  }

  /** 在浏览器预览图片 */
  @GetMapping("/preViewPicture/{objectName}")
  public void preViewPicture(
      @PathVariable("objectName") String objectName, HttpServletResponse response)
      throws Exception {
    response.setContentType("image/jpeg");
    try (ServletOutputStream out = response.getOutputStream()) {
      InputStream stream = minioUtil.getObject(objectName);
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      byte[] buffer = new byte[4096];
      int n = 0;
      while (-1 != (n = stream.read(buffer))) {
        output.write(buffer, 0, n);
      }
      byte[] bytes = output.toByteArray();
      out.write(bytes);
      out.flush();
    }
  }
}
