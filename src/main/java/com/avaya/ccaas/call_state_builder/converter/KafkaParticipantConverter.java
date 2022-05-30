package com.avaya.ccaas.call_state_builder.converter;

import com.avaya.calladapter.KafkaParticipant;
import com.avaya.ccaas.call_state_builder.redis.model.ParticipantContext;
import org.springframework.stereotype.Component;

@Component
public class KafkaParticipantConverter implements AvroConverter<KafkaParticipant, ParticipantContext> {
    @Override
    public ParticipantContext fromAvro(final KafkaParticipant avro) {
        return ParticipantContext.builder().id(avro.getId()).name(avro.getName()).build();
    }
}
