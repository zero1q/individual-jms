package com.shri.jms.listener;

import com.shri.jms.config.ActiveMQConfig;
import com.shri.jms.model.DummyClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseToMic {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = ActiveMQConfig.MY_QUEUE)
    public void listen(@Payload DummyClass dummyClass, @Headers MessageHeaders header, Message message) {
        log.info("<<<<< Mic is working properly. You can be silent now");
        log.info("<<<<< " + dummyClass.toString());
    }

    @JmsListener(destination = ActiveMQConfig.SEND_AND_RECEIVE_QUEUE)
    public void receiving(@Payload DummyClass dummyClass, @Headers MessageHeaders header, Message message) throws JMSException {
        log.info("<<<<< Your message is received");

        DummyClass payload = DummyClass.builder()
                .uuid(UUID.randomUUID())
                .message("Dummy class is receiving messages")
                .build();
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payload);
        log.info("<<<<< " + dummyClass.toString());
    }
}
