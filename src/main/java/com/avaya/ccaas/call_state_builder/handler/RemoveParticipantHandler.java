package com.avaya.ccaas.call_state_builder.handler;

import com.avaya.ccaas.call_state_builder.redis.model.CallContext;
import com.avaya.ccaas.call_state_builder.redis.repo.CallContextRepository;
import com.avaya.ccaas.participant_state_adapter.avro.ParticipantIdAvro;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RemoveParticipantHandler implements EventHandler<ParticipantIdAvro> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantIdAvro.class);
    private final CallContextRepository repository;

    @Override
    public void handle(final ParticipantIdAvro value) {
        LOGGER.info("ParticipantIdAvro {}", value);

        String callId = value.getCallId();
        String participantId = value.getId();
        CallContext call = repository.findOne(callId);
        checkCallContextOnNull(call);
        CallContext updatedCallContext = removeParticipant(call, participantId);
        repository.update(callId, updatedCallContext);

        LOGGER.info("Participant id={} has been removed from the call context id={}", participantId, callId);
    }

    private CallContext removeParticipant(CallContext call, String participantId) {
        call.getParticipants().removeIf(p -> p.getId().equals(participantId));
        return call;
    }
}
