package com.avaya.ccaas.call_state_builder.handler;

public interface EventHandler<K> {

    void handle(K value);
}
