package com.avaya.ccaas.call_state_builder.redis.repo;

import com.avaya.ccaas.call_state_builder.redis.model.CallContext;
import com.avaya.ccaas.call_state_builder.redis.model.ParticipantContext;

public interface CallContextRepository extends RedisRepository<String, CallContext> {

    @Override
    void save(final String key, final CallContext value);

    @Override
    void update(final String key, final CallContext value);

    @Override
    CallContext findOne(String key);

    @Override
    void remove(final String key);

    void removeParticipant(String callId, String participantId);

    void addParticipant(String key, ParticipantContext participantContext);
}
