package com.shri.jms.listener;

import com.shri.jms.config.ActiveMQConfig;
import com.shri.jms.model.DummyClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResponseToMic {

    @JmsListener(destination = ActiveMQConfig.MY_QUEUE)
    public void listen(@Payload DummyClass dummyClass, @Headers MessageHeaders header, Message message) {
        log.info("<<<<< Mic is working properly. You can be silent now");
        log.info("<<<<< " + dummyClass.toString());
    }
}
