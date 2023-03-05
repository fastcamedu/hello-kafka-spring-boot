package com.fastcamedu.hellokafkaspringboot.consumer;

import com.fastcamedu.hellokafkaspringboot.controller.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

//@Component
@Slf4j
public class HelloObjectMessageConsumer {

    private static final String TOPIC = "hello-topic";
    private static final String GROUP_ID = "hello-group-id";
    private static final String CONSUMER_ID = "hello-message-consumer-id";

    @KafkaListener(
            id = CONSUMER_ID,
            topics = TOPIC,
            groupId = GROUP_ID,
            containerFactory = "helloMessageKafkaListenerContainerFactory"
    )
    public void listen(@Payload HelloMessage helloMessage,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        log.info(String.format("<<< 토픽의 메세지 수신(%s): key = %-10s value = %s", topic, key, helloMessage.getMessage()));
    }
}
