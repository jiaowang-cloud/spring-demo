package jin.li.yun.com.service;

import com.alibaba.fastjson.JSON;
import jin.li.yun.com.common.entity.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static jin.li.yun.com.common.constant.Constant.RabbitMq.*;

/**
 * @author WangJiao
 * @since 2021/05/12
 */
@Service
@Slf4j
public class ConsumerDeadLetterService {
  @Resource private AmqpTemplate amqpTemplate;

  @RabbitListener(queues = DEAD_LETTER_QUEUE)
  public void receiveDeadLetter(UserResponse user) {
    log.info("receiveEmail2:死信队列接收[user:{}]", JSON.toJSONString(user));
  }
}
