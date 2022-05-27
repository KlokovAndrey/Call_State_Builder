package com.avaya.ccaas.call_state_builder.redis.repo;

import com.avaya.ccaas.call_state_builder.redis.model.CallContext;
import com.avaya.ccaas.call_state_builder.redis.model.ParticipantContext;

public interface CallContextRepository extends RedisRepository<String, CallContext> {

    void save(final String key, final CallContext value);

    void update(final String key, final CallContext value);

    CallContext findOne(String key);

    void remove(final String key);
}
