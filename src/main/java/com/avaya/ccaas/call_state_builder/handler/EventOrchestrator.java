package com.avaya.ccaas.call_state_builder.handler;

import com.avaya.calladapter.KafkaCreateCall;
import com.avaya.calladapter.KafkaDeleteCall;
import com.avaya.ccaas.call_state_builder.exception.HandlerException;
import com.avaya.ccaas.participant_state_adapter.avro.ParticipantIdAvro;
import com.avaya.ccaas.participant_state_adapter.avro.ParticipantStateAvro;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EventOrchestrator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventOrchestrator.class);

    private final CreateCallHandler createCallHandler;
    private final DeleteCallHandler deleteCallHandler;
    private final AddParticipantHandler addParticipantHandler;
    private final RemoveParticipantHandler removeParticipantHandler;

    public EventOrchestrator(
        final CreateCallHandler createCallHandler,
        final DeleteCallHandler deleteCallHandler,
        final AddParticipantHandler addParticipantHandler,
        final RemoveParticipantHandler removeParticipantHandler
    ) {
        this.createCallHandler = createCallHandler;
        this.deleteCallHandler = deleteCallHandler;
        this.addParticipantHandler = addParticipantHandler;
        this.removeParticipantHandler = removeParticipantHandler;
    }

    public void handle(final GenericRecord value) {
        if (KafkaCreateCall.class.getName().equals(value.getClass().getName())) {
            createCallHandler.handle((KafkaCreateCall) value);
        }
        else if (KafkaDeleteCall.class.getName().equals(value.getClass().getName())) {
            deleteCallHandler.handle((KafkaDeleteCall) value);
        }
        else if (ParticipantStateAvro.class.getName().equals(value.getClass().getName())) {
            addParticipantHandler.handle((ParticipantStateAvro) value);
        }
        else if (ParticipantIdAvro.class.getName().equals(value.getClass().getName())) {
            removeParticipantHandler.handle((ParticipantIdAvro) value);
        }
        else {
            LOGGER.error("Not possible to handle kafka message " + value);
            throw new HandlerException("Kafka type does not exist");
        }
    }
}
