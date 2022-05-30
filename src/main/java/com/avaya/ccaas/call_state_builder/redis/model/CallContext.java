package com.avaya.ccaas.call_state_builder.redis.model;

import com.avaya.calladapter.KafkaCreateCall;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CallContext implements Serializable {

    private String id;
    private String callerNumber;
    private String calledNumber;
    private String engagementDialogId;
    private String timestamp;
    private ArrayList<ParticipantContext> participants;
}
