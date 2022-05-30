package com.avaya.ccaas.call_state_builder.handler;

import com.avaya.ccaas.call_state_builder.exception.HandlerException;
import com.avaya.ccaas.call_state_builder.redis.model.CallContext;
import org.apache.avro.generic.GenericRecord;

public interface EventHandler<K extends GenericRecord> {

    void handle(K value);

    default void checkCallContextOnNull(CallContext call) {
        if (call == null) {
            throw new HandlerException("CallContext does not exist");
        }
    }
}
