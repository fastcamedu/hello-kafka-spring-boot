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
public class StringProducerController {

    private static final String TOPIC = "hello-string-topic";
    private static final String HELLO_MESSAGE_KEY = "hello-string-message-key";
    private final KafkaTemplate<String, String> helloStringMessageKafkaTemplate;

    @PostMapping("/kafka/string-message")
    public void publish(@RequestBody String helloStringMessage) {
        ListenableFuture<SendResult<String, String>> future = helloStringMessageKafkaTemplate.send(TOPIC, HELLO_MESSAGE_KEY, helloStringMessage);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info(String.format(">>> 토픽에 문자열 메시지 발행(%s): key = %-10s value = %s", TOPIC, HELLO_MESSAGE_KEY, helloStringMessage));
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(String.format(">>> 문자열 메세지 발행 실패, {}", ex.getMessage()));
            }
        });
    }
}
