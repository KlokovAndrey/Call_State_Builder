package com.avaya.ccaas.call_state_builder.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    private String redisUrl;
    private String password;

    public RedisConfig(
        @Value("${redis.url}") String redisUrl, @Value("${redis.password}") String password
    ) {
        this.redisUrl = redisUrl;
        this.password = password;
    }

    @Bean
    RedissonClient redisson() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());
        config.useSingleServer().setAddress(redisUrl).setPassword(password);
        return Redisson.create(config);
    }
}
