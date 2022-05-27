package com.avaya.ccaas.call_state_builder.handler;

import com.avaya.calladapter.KafkaDeleteCall;
import com.avaya.ccaas.call_state_builder.redis.repo.CallContextRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteCallHandler implements EventHandler<KafkaDeleteCall> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteCallHandler.class);
    private final CallContextRepository repository;

    @Override
    public void handle(final KafkaDeleteCall value) {
        LOGGER.info("KafkaDeleteCall " + value);
        String callId = value.getId();
        repository.delete(callId);
        LOGGER.info("Call context with id=" + callId + " has been deleted");
    }
}
