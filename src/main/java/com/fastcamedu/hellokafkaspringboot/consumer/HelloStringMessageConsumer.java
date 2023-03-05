package com.fastcamedu.hellokafkaspringboot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloStringMessageConsumer {
    @KafkaListener(
            topics = "hello-string-topic",
            groupId = "hello-string-group-id"
    )
    public void listenMessage(String message) {
        log.info("<<< 메세지 수신(hello-string-topic): " + message);
    }
}
