package com.avaya.ccaas.call_state_builder.redis.model;

import com.avaya.calladapter.KafkaParticipant;
import com.avaya.ccaas.participant_state_adapter.avro.ParticipantStateAvro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ParticipantContext {

    private String id;
    private String name;
}
