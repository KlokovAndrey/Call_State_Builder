package com.avaya.ccaas.call_state_builder.converter;

import com.avaya.ccaas.call_state_builder.redis.model.ParticipantContext;
import com.avaya.ccaas.participant_state_adapter.avro.ParticipantStateAvro;
import org.springframework.stereotype.Component;

@Component
public class ParticipantStateAvroConverter implements AvroConverter<ParticipantStateAvro, ParticipantContext> {
    @Override
    public ParticipantContext fromAvro(final ParticipantStateAvro avro) {
        return ParticipantContext.builder()
            .id(avro.getId())
            .name(avro.getName())
            .build();
    }
}
