package com.practice.highpower.controller;

import com.practice.highpower.entity.TweetEntity;
import com.practice.highpower.repository.TweetRepository;
import com.practice.highpower.service.SequenceGenerator;
import com.practice.highpower.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private TweetService tweetService;

    @GetMapping("/tweets")
    public Flux<TweetEntity> getAllTweets() {

        return tweetRepository.findAll();
    }

    @PostMapping()
    public Mono<TweetEntity> createTweets(@Valid @RequestBody TweetEntity tweetEntity) {
        return tweetService.createTweet(tweetEntity);
    }

    @GetMapping("/tweets/{id}")
    public Mono<ResponseEntity<TweetEntity>> getTweetById(@PathVariable(value = "id") Long tweetId) {

        return tweetService.getOne(tweetId)
                .map(tweet -> ResponseEntity.ok(tweet))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    @PutMapping("/tweets/{id}")
    public Mono<ResponseEntity<TweetEntity>> updateTweet(@PathVariable(value = "id") Long tweetId,
                                                         @Valid @RequestBody TweetEntity tweetEntity) {
        return tweetRepository.findById(tweetId)
                .flatMap(existingTweet -> {
                    existingTweet.setTweet(tweetEntity.getTweet());
                    return tweetRepository.save(existingTweet);
                })
                .map(updateTweet -> new ResponseEntity<>(updateTweet, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/tweets/{id}")
    public Mono<ResponseEntity<Void>> deleteTweet(@PathVariable(value = "id") Long tweetId) {

        return tweetRepository.findById(tweetId)
                .flatMap(existingTweet ->
                        tweetRepository.delete(existingTweet)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/stream/tweets", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TweetEntity> streamAllTweets() {
        return tweetService.fetchAll();
    }
}
