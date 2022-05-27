package com.avaya.ccaas.call_state_builder.redis.repo;

public interface RedisRepository<T, D> {

    void save(T key, D value);
    D findOne(T key);
    void update(T key, D value);
    void remove(T key);
}
