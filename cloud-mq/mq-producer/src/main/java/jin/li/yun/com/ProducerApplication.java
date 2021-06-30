package jin.li.yun.com;

import jin.li.yun.com.producer.confirm.RabbitConfirmCallback;
import jin.li.yun.com.producer.confirm.RabbitConfirmReturnCallBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@SpringBootApplication
@EnableFeignClients
@Slf4j
public class ProducerApplication {
  @Resource
  private RabbitTemplate rabbitTemplate;
  public static void main(String[] args) {
    SpringApplication.run(ProducerApplication.class, args);
  }

  @PostConstruct
  public void initRabbitTemplate() {
    // 设置生产者消息确认
    rabbitTemplate.setConfirmCallback(new RabbitConfirmCallback());
    rabbitTemplate.setReturnCallback(new RabbitConfirmReturnCallBack());
    log.info("initRabbitTemplate:[初始化生产者消息确认机制]" );
  }
}
