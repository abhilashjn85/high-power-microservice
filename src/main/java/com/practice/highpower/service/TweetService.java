package com.practice.highpower.service;

import com.practice.highpower.entity.TweetEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TweetService {

    Mono<TweetEntity> createTweet(TweetEntity tweet);
    Mono<TweetEntity> getOne(Long tweetId);
    Flux<TweetEntity> fetchAll();
    Mono<TweetEntity> update(String tweetId, TweetEntity tweetEntity);
}
