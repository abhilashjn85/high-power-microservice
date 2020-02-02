package com.practice.highpower.configuration;

import com.practice.highpower.entity.TweetEntity;
import com.practice.highpower.service.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
class RedisCacheConfiguration {
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, TweetEntity> tweetOps;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    public RedisCacheConfiguration(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, TweetEntity> tweetOps) {
        this.factory = factory;
        this.tweetOps = tweetOps;
    }

    @PostConstruct
    public void loadData() {
        factory.getReactiveConnection().serverCommands().flushAll().thenMany(
                Flux.just("Jet Black Redis", "Darth Redis", "Black Alert Redis")
                        .map(name -> new TweetEntity(sequenceGenerator.getNextSequenceId(TweetEntity.SEQUENCE_NAME), name))
                        .flatMap(tweet -> tweetOps.opsForValue().set(String.valueOf(tweet.getId()), tweet, Duration.of(30, ChronoUnit.SECONDS))))
                .thenMany(tweetOps.keys("*")
                        .flatMap(tweetOps.opsForValue()::get))
                .subscribe(System.out::println);
    }
}