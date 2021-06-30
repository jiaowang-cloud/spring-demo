package jin.li.yun.com.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static jin.li.yun.com.common.constant.Constant.RabbitMq.*;

/**
 * @author WangJiao
 * @since 2021/05/12
 */
@Slf4j
@Configuration
public class RabbitMqConfig {

  @Bean(name = "serializerMessageConverter")
  public MessageConverter getMessageConverter() {
    return new SimpleMessageConverter();
  }

  @Bean(name = "messagePropertiesConverter")
  public MessagePropertiesConverter getMessagePropertiesConverter() {
    return new DefaultMessagePropertiesConverter();
  }

  /**
   * 短信队列 绑定到死信交换机
   *
   * <p>设置消息TTL的两种方式<br>
   * (1) 设置队列属性值，队列中所有消息的过期时间都是相同的。<br>
   * (2) 设置消息本身的过期时间，则可能队列中每条消息的过期时间不同。<br>
   * 对于第一种设置队列TTL属性的方式，一旦消息过期，就会从队列中删除； <br>
   * 而第二种方法，即使消息过期，也不一定会马上从队列中删除。
   */
  @Bean
  public Queue smsQueue() {
    Map<String, Object> map = new HashMap<>(3);
    // 设置消息过期时长10秒,设置队列属性值，队列中所有消息的过期时间都是相同的
    map.put("x-message-ttl", MSG_EXPIRATION_TIME);
    map.put("x-dead-letter-exchange", EXCHANGE_DEAD_LETTER);
    map.put("x-dead-letter-routing-key", ROUTING_KEY_DEAD);
    return new Queue(TOPIC_SMS_QUEUE, true, false, false, map);
  }

  /** 声明邮件队列 FANOUT_EMAIL_QUEUE */
  @Bean
  public Queue emailQueue() {
    return new Queue(TOPIC_EMAIL_QUEUE);
  }

  /** 声明死信队列 DEAD_LETTER_QUEUE */
  @Bean
  public Queue deadLetterQueue() {
    return new Queue(DEAD_LETTER_QUEUE);
  }

  /** 声明Request交换机 */
  @Bean
  public Exchange exchangeRequestSms() {
    // durable(true) 持久化，mq重启之后交换机还在
    return ExchangeBuilder.topicExchange(EXCHANGE_REQUEST_SMS).durable(true).build();
  }

  /** 声明Reply交换机 */
  @Bean
  public Exchange exchangeReplySms() {
    // durable(true) 持久化，mq重启之后交换机还在
    return ExchangeBuilder.topicExchange(EXCHANGE_REPLY_SMS).durable(true).build();
  }

  /** 声明死信交换机 */
  @Bean
  public Exchange exchangeDeadLetter() {
    // durable(true) 持久化，mq重启之后交换机还在
    return ExchangeBuilder.topicExchange(EXCHANGE_DEAD_LETTER).durable(true).build();
  }

  /** 绑定交换机和队列 ROUTING_KEY_SMS队列绑定交换机，指定routingKey */
  @Bean
  public Binding bindingRoutingKeySms(Queue smsQueue, Exchange exchangeRequestSms) {
    return BindingBuilder.bind(smsQueue).to(exchangeRequestSms).with(ROUTING_KEY_SMS).noargs();
  }

  /** 绑定交换机和队列 ROUTING_KEY_EMAIL 队列绑定交换机，指定routingKey */
  @Bean
  public Binding bindingRoutingKeyEmail(Queue emailQueue, Exchange exchangeReplySms) {
    return BindingBuilder.bind(emailQueue).to(exchangeReplySms).with(ROUTING_KEY_EMAIL).noargs();
  }

  /** 绑定死信队列到死信交换机 DEAD_LETTER_QUEUE 队列绑定交换机，指定routingKey */
  @Bean
  public Binding bindingRoutingKeyDeadLetter(Queue deadLetterQueue, Exchange exchangeDeadLetter) {
    return BindingBuilder.bind(deadLetterQueue)
        .to(exchangeDeadLetter)
        .with(ROUTING_KEY_DEAD)
        .noargs();
  }
}
