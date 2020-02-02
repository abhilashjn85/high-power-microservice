package com.practice.highpower.service.impl;

import com.practice.highpower.entity.TweetEntity;
import com.practice.highpower.repository.TweetRepository;
import com.practice.highpower.service.SequenceGenerator;
import com.practice.highpower.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Service
public class TweetServiceImpl implements TweetService {

    private final ReactiveRedisOperations<String, TweetEntity> tweetOps;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    TweetServiceImpl(ReactiveRedisOperations<String, TweetEntity> tweetOps) {
        this.tweetOps = tweetOps;
    }

    @Override
    public Mono<TweetEntity> createTweet(TweetEntity tweet) {

        tweet.setId(sequenceGenerator.getNextSequenceId(TweetEntity.SEQUENCE_NAME));
        return tweetRepository.save(tweet)
                .flatMap(entity -> tweetOps.opsForValue().set(String.valueOf(entity.getId()), entity, Duration.of(1, ChronoUnit.MINUTES)))
                .then(Mono.just(tweet));
    }

    @Override
    public Mono<TweetEntity> getOne(Long tweetId) {

        return tweetOps.opsForValue()
                .get(String.valueOf(tweetId))
                .switchIfEmpty(tweetRepository.findById(tweetId))
                .flatMap(entity ->  tweetOps.opsForValue().set(String.valueOf(tweetId), entity, Duration.of(1, ChronoUnit.MINUTES))
                        .then(Mono.just(entity)));
    }

    @Override
    public Flux<TweetEntity> fetchAll() {
        return tweetRepository.findAll();
    }

    @Override
    public Mono<TweetEntity> update(String tweetId, TweetEntity tweetEntity) {

        return null;
    }
}
