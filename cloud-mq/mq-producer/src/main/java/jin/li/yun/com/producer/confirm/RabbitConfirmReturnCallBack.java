package jin.li.yun.com.producer.confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Return 消息机制 消息发送交换机返回机制
 *
 * @author WangJiao
 * @since 2021/05/20
 */
@Slf4j
public class RabbitConfirmReturnCallBack implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
    log.info(
        "returnedMessage:消息发送交换机返回机制[message:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}]",
        message,
        replyCode,
        replyText,
        exchange,
        routingKey);
    }
}
