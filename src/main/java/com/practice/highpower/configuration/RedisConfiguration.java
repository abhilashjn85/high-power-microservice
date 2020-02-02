package com.practice.highpower.configuration;

import com.practice.highpower.entity.TweetEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    ReactiveRedisOperations<String, TweetEntity> redisOperations(ReactiveRedisConnectionFactory factory) {

        Jackson2JsonRedisSerializer<TweetEntity> serializer = new Jackson2JsonRedisSerializer<>(TweetEntity.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, TweetEntity> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, TweetEntity> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
