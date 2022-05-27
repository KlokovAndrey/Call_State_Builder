package com.avaya.ccaas.call_state_builder.redis.repo;

import com.avaya.ccaas.call_state_builder.redis.model.CallContext;
import com.avaya.ccaas.call_state_builder.redis.model.ParticipantContext;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class CallContextRepository implements RedisRepository<String, CallContext>{

    private final RedissonClient client;
    private String tableName = "call-context";
    private RMap<String, CallContext> map;

    @PostConstruct
    private void init(){
        map = client.getMap(tableName);
    }

    @Override
    public void save(final String key, final CallContext value) {
        map.put(key, value);
    }

    @Override
    public void update(final String key, final CallContext value) {
        map.replace(key, value);
    }

    public CallContext findOne(String key){
        return map.get(key);
    }

    @Override
    public void delete(final String key) {
        map.fastRemove(key);
    }

    public void removeParticipant(String callId, String participantId){
        CallContext call = findOne(callId);
        call.getParticipants().removeIf(p -> p.getId().equals(participantId));
        update(callId, call);
    }

    public void addParticipant(String key, ParticipantContext participantContext){
        CallContext call = findOne(key);
        call.getParticipants().add(participantContext);
        update(key, call);
    }
}
