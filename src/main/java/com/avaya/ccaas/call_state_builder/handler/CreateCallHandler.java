package com.avaya.ccaas.call_state_builder.handler;

import com.avaya.calladapter.KafkaCreateCall;
import com.avaya.ccaas.call_state_builder.redis.CallContext;
import com.avaya.ccaas.call_state_builder.redis.ParticipantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateCallHandler implements EventHandler<KafkaCreateCall>{

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCallHandler.class);

    @Override
    public void handle(final KafkaCreateCall value) {
        LOGGER.info("KafkaCreateCall " + value);
        CallContext callContext = CallContext.createFromKafkaMessage(value);
        ParticipantContext participantContext = ParticipantContext.createFromKafkaMessage(value);
        //сохранить в редис
    }
}
