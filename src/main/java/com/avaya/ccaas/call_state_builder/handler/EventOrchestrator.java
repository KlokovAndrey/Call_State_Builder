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

import java.util.Map;

@Service
public class EventOrchestrator<T extends GenericRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventOrchestrator.class);

    private Map<Class<? extends GenericRecord>, EventHandler<T>> map;

    public EventOrchestrator(
        final CreateCallHandler createCallHandler,
        final DeleteCallHandler deleteCallHandler,
        final AddParticipantHandler addParticipantHandler,
        final RemoveParticipantHandler removeParticipantHandler
    ) {

        map = Map.of(KafkaCreateCall.class, (EventHandler<T>) createCallHandler,
            KafkaDeleteCall.class, (EventHandler<T>)deleteCallHandler,
            ParticipantStateAvro.class, (EventHandler<T>)addParticipantHandler,
            ParticipantIdAvro.class, (EventHandler<T>)removeParticipantHandler
        );
    }

    public void handle(final T value) {
        map.get(value.getClass()).handle(value);
    }
}
