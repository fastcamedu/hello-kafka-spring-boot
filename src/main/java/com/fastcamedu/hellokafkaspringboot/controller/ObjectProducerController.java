package com.fastcamedu.hellokafkaspringboot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ObjectProducerController {

    private static final String TOPIC = "hello-object-topic";
    private static final String HELLO_MESSAGE_KEY = "hello-object-message-key";

    private final KafkaTemplate<String, HelloMessage> helloObjectMessageKafkaTemplate;

    @PostMapping("/kafka/object-message")
    public void publish(@RequestBody HelloMessage helloMessage) {
        ListenableFuture<SendResult<String, HelloMessage>> future = helloObjectMessageKafkaTemplate.send(TOPIC, HELLO_MESSAGE_KEY, helloMessage);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, HelloMessage> result) {
                log.info(String.format(">>> 토픽에 메시지 발생(%s): key = %-10s value = %s", TOPIC, HELLO_MESSAGE_KEY, helloMessage.getMessage()));
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(String.format(">>> Message sending is Failed, {}", ex.getMessage()));
            }
        });
    }

}
