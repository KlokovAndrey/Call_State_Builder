package com.avaya.ccaas.call_state_builder.handler;

import com.avaya.calladapter.KafkaDeleteCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteCallHandler implements EventHandler<KafkaDeleteCall> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteCallHandler.class);

    @Override
    public void handle(final KafkaDeleteCall value) {
        LOGGER.info("KafkaDeleteCall " + value);
        //достать из редиса по id и удалить
    }
}
