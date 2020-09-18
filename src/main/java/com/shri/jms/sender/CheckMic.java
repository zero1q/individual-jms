package com.shri.jms.sender;

import com.shri.jms.config.ActiveMQConfig;
import com.shri.jms.model.DummyClass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class CheckMic {

    private final JmsTemplate template;

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
}
