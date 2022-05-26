package com.avaya.ccaas.call_state_builder.redis;

import com.avaya.calladapter.KafkaCreateCall;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CallContext {

    private String id;
    private String callerNumber;
    private String calledNumber;
    private String engagementDialogId;
    private Instant timestamp;

    public static CallContext createFromKafkaMessage(KafkaCreateCall call) {
        return CallContext.builder()
            .id(call.getId())
            .callerNumber(call.getCallerNumber())
            .calledNumber(call.getCalledNumber())
            .engagementDialogId(call.getEngagementDialogId())
            .timestamp(call.getTimestamp())
            .build();
    }
}
