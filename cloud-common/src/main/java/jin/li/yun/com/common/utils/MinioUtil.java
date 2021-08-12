package jin.li.yun.com.common.utils;

import com.alibaba.fastjson.JSON;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import jin.li.yun.com.common.config.MinioProp;
import jin.li.yun.com.common.entity.response.minio.FileUploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @author WangJiao
 * @since 2021/08/05
 */
@Slf4j
@Component
public class MinioUtil {

  @Resource private MinioProp minioProp;

  @Resource private MinioClient client;

  /** 创建bucket */
  public void createBucket(String bucketName) throws Exception {
    boolean bucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
   // boolean bucketExists = client.bucketExists(bucketName);
    if (!bucketExists) {
       client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
     // client.makeBucket(bucketName);
      log.info("createBucket:创建桶名[bucketName:{}]", bucketName);
    }else {
      log.info("createBucket:桶名已经存在[bucketName:{}]", bucketName);
    }
  }

  /** 上传文件 */
  public FileUploadResponse uploadFile(MultipartFile file) throws Exception {
    String bucketName = minioProp.getBucketName();
    log.info("uploadFile:[minioProp:{}]", JSON.toJSONString(minioProp));
    // 判断文件是否为空
    if (null == file || 0 == file.getSize()) {
      log.error("uploadFile:文件为空[file:{}]", file);
      return null;
    }
    // 判断存储桶是否存在  不存在则创建
     createBucket(bucketName);
    // 文件名
    String originalFilename = file.getOriginalFilename();
    log.info("uploadFile:源文件名称[originalFilename:{}]", originalFilename);
    // 新的文件名 = 存储桶文件名_时间戳.后缀名
    assert originalFilename != null;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String fileName =
        bucketName
            + "_"
            + System.currentTimeMillis()
            + "_"
            + format.format(new Date())
            + "_"
            + new Random().nextInt(1000)
            + originalFilename.substring(originalFilename.lastIndexOf("."));
    log.info("uploadFile:新文件名称[fileName:{}]", fileName);
    // 开始上传
//    client.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
//            file.getInputStream(), file.getSize(), -1)
//            .contentType(file.getContentType())
//            .build());
    client.putObject(PutObjectArgs.builder()
            .stream(file.getInputStream(), file.getSize(),PutObjectArgs.MIN_MULTIPART_SIZE)
            .object(fileName)
            .contentType(file.getContentType())
            .bucket(bucketName)
            .build());
//    PutObjectOptions putObjectOptions = new PutObjectOptions(file.getSize(), PutObjectOptions.MIN_MULTIPART_SIZE);
//    // 文件的ContentType
//    putObjectOptions.setContentType(file.getContentType());
//    client.putObject(bucketName,fileName,file.getInputStream(),putObjectOptions);
    String url = minioProp.getEndpoint() + "/" + bucketName + "/" + fileName;
    String urlHost = minioProp.getFilHost() + "/" + bucketName + "/" + fileName;
    log.info("上传文件成功url ：[{}], urlHost ：[{}]", url, urlHost);
    return new FileUploadResponse(url, urlHost);
  }

  /**
   * 获取全部bucket
   *
   * @return list
   */
  public List<Bucket> getAllBuckets() throws Exception {
    return client.listBuckets();
  }

  /**
   * 根据bucketName获取信息
   *
   * @param bucketName bucket名称
   */
  public Optional<Bucket> getBucket(String bucketName)
          throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException,
          InvalidResponseException, InternalException, ErrorResponseException, ServerException,
          XmlParserException, io.minio.errors.ServerException {
    return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
  }

  /**
   * 根据bucketName删除信息
   *
   * @param bucketName bucket名称
   */
  public void removeBucket(String bucketName) throws Exception {
    client.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
   // client.removeBucket(bucketName);
  }

  /**
   * 获取⽂件外链
   *
   * @param bucketName bucket名称
   * @param objectName ⽂件名称
   * @param expires 过期时间 <=7
   * @return url
   */
  public String getObjectURL(String bucketName, String objectName, Integer expires)
      throws Exception {
//    return client.getPresignedObjectUrl(
//        GetPresignedObjectUrlArgs.builder()
//            .bucket(bucketName)
//            .object(objectName)
//            .expiry(expires)
//            .build());
    return null;
  }

  /**
   * 获取⽂件
   *
   * @param objectName ⽂件名称
   * @return ⼆进制流
   */
  public InputStream getObject(String objectName) throws Exception {
    String bucketName = minioProp.getBucketName();
    return client.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
//    return client.getObject(bucketName, objectName);
  }

  /**
   * 上传⽂件
   *
   * @param bucketName bucket名称
   * @param objectName ⽂件名称
   * @param stream ⽂件流
   * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
   */
  public void putObject(String bucketName, String objectName, InputStream stream) throws Exception {
//    client.putObject(
//        PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
//                stream, stream.available(), -1)
//            .contentType(objectName.substring(objectName.lastIndexOf(".")))
//            .build());
  }

  /**
   * 上传⽂件
   *
   * @param bucketName bucket名称
   * @param objectName ⽂件名称
   * @param stream ⽂件流
   * @param size ⼤⼩
   * @param contextType 类型
   * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
   */
  public void putObject(
      String bucketName, String objectName, InputStream stream, long size, String contextType)
      throws Exception {
//    client.putObject(
//        PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, size, -1)
//            .contentType(contextType)
//            .build());
  }

  /**
   * 获取⽂件信息
   *
   * @param bucketName bucket名称
   * @param objectName ⽂件名称
   * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
   */
//  public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
//    return client.statObject(
//        StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
//  }

  /**
   * 删除⽂件
   *
   * @param objectName ⽂件名称
   * @throws Exception https://docs.minio.io/cn/java-client-apireference.html#removeObject
   */
  public void removeObject(String objectName) throws Exception {
    String bucketName = minioProp.getBucketName();
    client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
//    client.removeObject(bucketName,objectName);
  }
}
