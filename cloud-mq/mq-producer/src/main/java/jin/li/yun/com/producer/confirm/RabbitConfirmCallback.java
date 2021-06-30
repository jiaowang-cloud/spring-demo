package jin.li.yun.com.producer.confirm;

import com.alibaba.fastjson.JSON;
import jin.li.yun.com.producer.utils.CacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

import java.util.Objects;

/**
 * 生产端 Confirm 消息确认机制
 *
 * @author WangJiao
 * @since 2021/05/20
 */
@Slf4j
public class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback {
  @Override
  public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    log.info(
        "confirm:生产端 Confirm 消息确认机制[correlationData:{}, ack:{}, cause:{}]",
        JSON.toJSONString(correlationData),
        ack,
        cause);
    if (ack && Objects.nonNull(correlationData)) {
      String id = correlationData.getId();
      String mapId = CacheUtils.get(id);
      log.info("confirm:[mapId:{}]", mapId);
      if (Objects.equals(id, mapId)) {
        log.info("confirm:消息发送成功!!!可执行其他事情[id:{}]", id);
        CacheUtils.clear();
      }
    }
  }
}
