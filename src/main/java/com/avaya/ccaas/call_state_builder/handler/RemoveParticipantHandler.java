package com.avaya.ccaas.call_state_builder.handler;

import com.avaya.ccaas.call_state_builder.redis.repo.CallContextRepository;
import com.avaya.ccaas.participant_state_adapter.avro.ParticipantIdAvro;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RemoveParticipantHandler implements EventHandler<ParticipantIdAvro>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantIdAvro.class);
    private final CallContextRepository repository;

    @Override
    public void handle(final ParticipantIdAvro value) {
        LOGGER.info("ParticipantIdAvro " + value);
        String callId = value.getCallId();
        String participantId = value.getId();
        repository.removeParticipant(callId, participantId);
        LOGGER.info("Participant {id=" + participantId +
            "} has been removed from the call context {id=" + callId + "}");
    }
}
