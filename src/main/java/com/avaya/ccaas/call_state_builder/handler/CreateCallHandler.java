package com.avaya.ccaas.call_state_builder.handler;

import com.avaya.calladapter.KafkaCreateCall;
import com.avaya.ccaas.call_state_builder.converter.KafkaCreateCallConverter;
import com.avaya.ccaas.call_state_builder.redis.model.CallContext;
import com.avaya.ccaas.call_state_builder.redis.repo.CallContextRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateCallHandler implements EventHandler<KafkaCreateCall> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCallHandler.class);
    private final CallContextRepository repository;
    private final KafkaCreateCallConverter converter;

    @Override
    public void handle(final KafkaCreateCall value) {
        LOGGER.info("KafkaCreateCall {}", value);

        CallContext callContext = converter.fromAvro(value);
        repository.save(callContext.getId(), callContext);

        LOGGER.info("Call context with id={} has been saved", callContext.getId());
    }
}
