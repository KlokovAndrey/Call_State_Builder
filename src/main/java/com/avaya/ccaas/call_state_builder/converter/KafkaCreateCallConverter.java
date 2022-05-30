package com.avaya.ccaas.call_state_builder.converter;

import com.avaya.calladapter.KafkaCreateCall;
import com.avaya.ccaas.call_state_builder.redis.model.CallContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class KafkaCreateCallConverter implements AvroConverter<KafkaCreateCall, CallContext> {

    private final KafkaParticipantConverter converter;
    @Override
    public CallContext fromAvro(final KafkaCreateCall avro) {
        return CallContext.builder()
            .id(avro.getId())
            .callerNumber(avro.getCallerNumber())
            .calledNumber(avro.getCalledNumber())
            .engagementDialogId(avro.getEngagementDialogId())
            .timestamp(avro.getTimestamp().toString())
            .participants(new ArrayList<>(Arrays.asList(converter.fromAvro(avro.getParticipant()))))
            .build();
    }
}
