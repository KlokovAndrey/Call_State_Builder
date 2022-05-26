package com.avaya.ccaas.call_state_builder.redis;

import com.avaya.calladapter.KafkaCreateCall;
import com.avaya.calladapter.KafkaParticipant;
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

    private String callId;
    private String id;
    private String name;

    public static ParticipantContext createFromKafkaMessage(final KafkaCreateCall call) {
        return ParticipantContext.builder()
            .callId(call.getId())
            .id(call.getParticipant().getId())
            .name(call.getParticipant().getName())
            .build();
    }
}
