package com.shri.jms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shri.jms.config.ActiveMQConfig;
import com.shri.jms.model.DummyClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class CheckMic {

    private final JmsTemplate template;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        log.info(">>>>> Mic Check");
        DummyClass obj = DummyClass.builder()
                .uuid(UUID.randomUUID())
                .message("Dummy class is sending messages")
                .build();
        template.convertAndSend(ActiveMQConfig.MY_QUEUE, obj);
        log.info(">>>>> hello from the other side. mic is working fine");
    }

    @Scheduled(fixedRate = 1000)
    public void sendAndReceiveMessage() throws JMSException {
        log.info(">>>>> Mic Check");
        DummyClass obj = DummyClass.builder()
                .uuid(UUID.randomUUID())
                .message("Dummy class is sending messages")
                .build();
        Message message = template.sendAndReceive(ActiveMQConfig.SEND_AND_RECEIVE_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message micCheck = null;

                try {
                    session.createTextMessage(objectMapper.writeValueAsString(obj));
                    micCheck.setStringProperty("_type", "com.shri.jms.model.DummyClass");
                    log.info(">>>>> sending ...");
                    return micCheck;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new JMSException("Exception occurred");
                }
            }
        });
        log.info(">>>>> " + message.getBody(String.class));
    }
}
