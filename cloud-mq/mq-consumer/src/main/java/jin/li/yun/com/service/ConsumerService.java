package jin.li.yun.com.service;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import jin.li.yun.com.common.entity.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static jin.li.yun.com.common.constant.Constant.RabbitMq.*;

/**
 * @author WangJiao
 * @since 2021/05/12
 */
@Service
@Slf4j
public class ConsumerService {
  @Resource private AmqpTemplate amqpTemplate;
  @Resource private RabbitTemplate rabbitTemplate;
  @Resource private MessagePropertiesConverter messagePropertiesConverter;

  //  @RabbitListener(queues=FANOUT_EMAIL_QUEUE)
  //  public void receiveEmail1(UserResponse build) {
  //    log.info("receiveEmail1:邮件队列1接收[build:{}]", build);
  //    //数据处理，返回的Message
  //    UserResponse user =
  //            UserResponse.builder()
  //                    .id(545455454L)
  //                    .avatar("ddd")
  //                    .name("小王")
  //                    .status(4)
  //                    .tel("15678356253")
  //                    .build();
  //    amqpTemplate.convertAndSend(EXCHANGE_USER_NAME, "inform.email", build);
  //  }
  //
  //  @RabbitListener(queues=FANOUT_EMAIL_QUEUE)
  //  public void receiveEmail2(UserResponse user) {
  //    log.info("receiveEmail2:邮件队列2接收[user:{}]", JSON.toJSONString(user));
  //  }
  //
  //  public Message con(String s, String id) {
  //    MessageProperties mp = new MessageProperties();
  //    byte[] src = s.getBytes(StandardCharsets.UTF_8);
  //    mp.setContentType("application/json");
  //    mp.setContentEncoding("UTF-8");
  //    mp.setCorrelationId(String.valueOf(SnowflakeIdWorkerUtil.generateId()));
  //
  //    return new Message(src, mp);
  //  }

  @RabbitListener(queues = TOPIC_SMS_QUEUE)
  public void receiveSms(Message message, Channel channel, @Headers Map<String, Object> headers) {

    /**
     * 保证消息幂等性<br>
     * 以主键ID作为消息ID，即可保证消息的幂等性，消费过程为：
     *
     * <p>消费者获取到消息后先根据id去查询redis/db是否存在该消息；
     *
     * <p>如果不存在，则正常消费，消费完毕后写入redis/db；
     *
     * <p>如果存在，则证明消息被消费过，直接丢弃
     */
    try {
      byte[] body = message.getBody();
      String s = new String(body, StandardCharsets.UTF_8);
      log.info("receiveSms:短信队列[build:{}]", JSON.toJSONString(s));
      // 手工ack
      long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
      channel.basicAck(deliveryTag, true);
      log.info("==============查询消费者结束======deliveryTag:{}====", deliveryTag);
    } catch (Exception e) {
      log.error("==============查询消费者异常，异常信息：{}", e.getMessage());
      e.printStackTrace();
    }
  }

    @RabbitListener(queues = TOPIC_SMS_QUEUE)
    public void onMessage(Message message) throws Exception {
      String msg = new String(message.getBody(), StandardCharsets.UTF_8);
      log.info("onMessage:接收到消息[msg:{}]", msg);
      try {

        MessageProperties messageProperties = message.getMessageProperties();
        AMQP.BasicProperties rabbitMQProperties =
            messagePropertiesConverter.fromMessageProperties(messageProperties, "UTF-8");
        String messageContent = null;
        messageContent = new String(message.getBody(), StandardCharsets.UTF_8);
        String consumerTag = messageProperties.getConsumerTag();
        log.info("onMessage:接收到消息[messageContent:{}]", messageContent);

        // RPC回复 ，发送消息
        UserResponse build =
            UserResponse.builder()
                .id(43251234L)
                .name("小明")
                .avatar("asfjdl")
                .status(2)
                .tel("15675458732")
                .build();
        String cId = rabbitMQProperties.getCorrelationId();
        AMQP.BasicProperties replyRabbitMQProps =
            new AMQP.BasicProperties(
                "text/plain",
                "UTF-8",
                null,
                2,
                0,
                cId,
                null,
                null,
                null,
                null,
                null,
                null,
                consumerTag,
                null);
        Envelope replyEnvelope =
            new Envelope(
                messageProperties.getDeliveryTag(), true, EXCHANGE_REPLY_SMS, ROUTING_KEY_EMAIL);

        MessageProperties replyMessageProperties =
            messagePropertiesConverter.toMessageProperties(
                replyRabbitMQProps, replyEnvelope, "UTF-8");

        Message replyMessage =
            MessageBuilder.withBody(JSON.toJSONString(build).getBytes(StandardCharsets.UTF_8))
                .andProperties(replyMessageProperties)
                .build();

        amqpTemplate.send(EXCHANGE_REPLY_SMS, "inform.email", replyMessage);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
}
