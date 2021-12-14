package com.nttdata.bootcamp.yankiservice.infrastructure.spring.config;

import com.nttdata.bootcamp.yankiservice.domain.Yanki;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


@EnableCaching
@Configuration
public class YankiConfiguration {
    @Bean
    ReactiveRedisOperations<String, Yanki> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Yanki> serializer = new Jackson2JsonRedisSerializer<>(Yanki.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Yanki> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Yanki> context = builder.value(serializer).build();

        ReactiveRedisTemplate<String, Yanki> rt = new ReactiveRedisTemplate<>(factory, context);

        rt.expire("*", Duration.ofMillis(1000));
        return rt;
    }

}