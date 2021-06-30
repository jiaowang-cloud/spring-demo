package jin.li.yun.com.producer.service;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP;
import jin.li.yun.com.common.api.ApiResult;
import jin.li.yun.com.common.entity.response.UserResponse;
import jin.li.yun.com.common.utils.SnowflakeIdWorkerUtil;
import jin.li.yun.com.producer.utils.CacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

import static jin.li.yun.com.common.constant.Constant.RabbitMq.*;

/**
 * @author WangJiao
 * @since 2021/05/12
 */
@Service
@Slf4j
public class ProducerService {
  @Resource private AmqpTemplate amqpTemplate;
  @Resource private RabbitTemplate rabbitTemplate;
  @Resource private MessagePropertiesConverter messagePropertiesConverter;

  //  @GetMapping("/send-msg2")
  //  public ApiResult sendEmail() {
  //
  //    // 使用 convertSendAndReceive 方法完成rpc调用
  //    UserResponse build =
  //        UserResponse.builder()
  //            .id(545455454L)
  //            .avatar("ttt")
  //            .name("李四")
  //            .status(2)
  //            .tel("13987653490")
  //            .build();
  //
  //    Object o =
  //        amqpTemplate.convertSendAndReceive(
  //            EXCHANGE_REQUEST_SMS,
  //            "inform.email",
  //            build,
  //            message -> {
  //              MessageProperties messageProperties = message.getMessageProperties();
  //              messageProperties .setReplyTo(FANOUT_SMS_QUEUE);
  //              messageProperties
  // .setCorrelationId(SnowflakeIdWorkerUtil.generateId().toString());
  //              messageProperties.setContentType("application/json");
  //              messageProperties.setContentEncoding("UTF-8");
  //              return message;
  //            });
  //    log.info("sendEmail:[send end]");
  //    return ApiResult.ok(JSON.toJSONString(o));
  //  }

  @GetMapping("/send-msg")
  public UserResponse sendMsg() {

    UserResponse build =
        UserResponse.builder().id(43251234L).name("张三").status(2).tel("15675458732").build();
    amqpTemplate.convertAndSend(
        EXCHANGE_REQUEST_SMS,
        "inform.sms",
        build,
        message -> {
          MessageProperties messageProperties = message.getMessageProperties();
          //          messageProperties.setReplyTo(FANOUT_EMAIL_QUEUE);
          //
          // messageProperties.setCorrelationId(SnowflakeIdWorkerUtil.generateId().toString());
          //          messageProperties.setContentType("application/json");
          //          messageProperties.setContentEncoding("UTF-8");
          // 设置消息过期时间10秒
          messageProperties.setExpiration("10000");
          return message;
        });
    log.info("sendMsg:[生产者发送消息]");
    return build;
  }

  @GetMapping("/send-msg2")
  public UserResponse sendMsg2() {

    UserResponse build =
        UserResponse.builder()
            .id(SnowflakeIdWorkerUtil.generateId())
            .name("马云")
            .status(2)
            .tel("15675458732")
            .build();
    // 全局唯一
    String id = build.getId().toString();
    CorrelationData correlationData = new CorrelationData(id);
    CacheUtils.put(id);
    rabbitTemplate.convertSendAndReceive(EXCHANGE_REQUEST_SMS, "inform.sms", build, correlationData);

    log.info("sendMsg:[生产者发送消息]");
    return build;
  }

  @GetMapping("send")
  public ApiResult send() {
    // 定义响应回调队列
    String correlationId = SnowflakeIdWorkerUtil.generateId().toString();
    Date sendTime = new Date();
    AMQP.BasicProperties props =
        new AMQP.BasicProperties(
            "text/plain",
            "UTF-8",
            null,
            2,
            0,
            correlationId,
            TOPIC_EMAIL_QUEUE,
            null,
            null,
            sendTime,
            null,
            null,
            "SpringProducer",
            null);

    MessageProperties sendMessageProperties =
        messagePropertiesConverter.toMessageProperties(props, null, "UTF-8");
    sendMessageProperties.setReceivedExchange(EXCHANGE_REPLY_SMS);
    sendMessageProperties.setReceivedRoutingKey(ROUTING_KEY_EMAIL);
    sendMessageProperties.setRedelivered(true);
    UserResponse build =
        UserResponse.builder().id(43251234L).name("马云44ee").status(2).tel("15675458732").build();
    try {
      // 发送并接收回执
      Message sendMessage =
          MessageBuilder.withBody(JSON.toJSONString(build).getBytes(StandardCharsets.UTF_8))
              .andProperties(sendMessageProperties)
              .setReplyTo(TOPIC_EMAIL_QUEUE)
              .setCorrelationId(correlationId)
              .build();

      Message replyMessage =
          amqpTemplate.sendAndReceive(EXCHANGE_REQUEST_SMS, "inform.sms", sendMessage);
      log.info("sendMsg:[生产者发送消息]");

      String replyMessageContent = null;
      replyMessageContent = new String(replyMessage.getBody(), StandardCharsets.UTF_8);
      log.info("send:接收到回执[replyMessageContent:{}]", replyMessageContent);
      return ApiResult.ok(replyMessageContent);
    } catch (Exception e) {
      log.error("send:[errorMsg:{}]", e.getMessage());
      e.printStackTrace();
    }
    return ApiResult.fail("fail!!!!");
  }
}
