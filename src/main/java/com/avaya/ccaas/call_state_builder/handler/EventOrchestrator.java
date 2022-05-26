package com.avaya.ccaas.call_state_builder.handler;

import com.avaya.calladapter.KafkaCreateCall;
import com.avaya.calladapter.KafkaDeleteCall;
import com.avaya.ccaas.call_state_builder.exception.HandlerException;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EventOrchestrator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventOrchestrator.class);

    private final CreateCallHandler createCallHandler;
    private final DeleteCallHandler deleteCallHandler;

    public EventOrchestrator(final CreateCallHandler createCallHandler, final DeleteCallHandler deleteCallHandler) {
        this.createCallHandler = createCallHandler;
        this.deleteCallHandler = deleteCallHandler;
    }

    public void handle(final GenericRecord value) {
       if (KafkaCreateCall.class.getName().equals(value.getClass().getName())) {
           createCallHandler.handle((KafkaCreateCall) value);
       } else if (KafkaDeleteCall.class.getName().equals(value.getClass().getName())) {
           deleteCallHandler.handle((KafkaDeleteCall) value);
       } else {
           LOGGER.error("Not possible to handle kafka message " + value);
           throw new HandlerException("Kafka type does not exist");
       }
    }
}
