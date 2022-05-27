package com.avaya.ccaas.call_state_builder.redis.repo;

public interface RedisRepository<T, D> {

    void save(T key, D value);
    void update(T key, D value);
    void delete(T key);
}
