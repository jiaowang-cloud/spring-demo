package jin.li.yun.com.producer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import io.swagger.annotations.ApiOperation;
import jin.li.yun.com.common.api.ApiResult;
import jin.li.yun.com.common.entity.request.ContractTypeLabelUpdRequest;
import jin.li.yun.com.common.entity.response.UserResponse;
import jin.li.yun.com.common.utils.SnowflakeIdWorkerUtil;
import jin.li.yun.com.producer.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static jin.li.yun.com.common.constant.Constant.RabbitMq.*;

/**
 * @author WangJiao
 * @since 2021/05/12
 */
@RestController
@Slf4j
public class ProducerController {
  @Resource private ProducerService producerService;

  @GetMapping("/send-msg")
  public ApiResult sendMsg() {
    UserResponse userResponse = producerService.sendMsg();
    log.info("sendMsg:[生产者发送消息]");
    return ApiResult.ok(userResponse);
  }

  @GetMapping("/send-msg2")
  public ApiResult sendMsg2() {

    producerService.sendMsg2();
    log.info("sendMsg:[生产者发送消息]");
    return ApiResult.ok();
  }

  private RabbitProperties.Cache.Channel channel;

  @GetMapping("send")
  public ApiResult send() {
    producerService.send();
    return ApiResult.ok("ok!!!!");
  }

  @PostMapping("/label")
  @ApiOperation("修改label")
  public ApiResult updLabel(@Valid @RequestBody JSONObject labelUpdRequest) {
    log.info("login:[labelUpdRequest:{}]", JSON.toJSONString(labelUpdRequest));

    return ApiResult.ok(labelUpdRequest);
  }
}
