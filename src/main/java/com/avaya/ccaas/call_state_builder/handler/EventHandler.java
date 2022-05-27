package com.avaya.ccaas.call_state_builder.handler;

import com.avaya.ccaas.call_state_builder.exception.HandlerException;
import com.avaya.ccaas.call_state_builder.redis.model.CallContext;

public interface EventHandler<K> {

    void handle(K value);

    default void checkCallContext(CallContext call) {
        if (call == null) {
            throw new HandlerException("CallContext does not exist");
        }
    }
}
