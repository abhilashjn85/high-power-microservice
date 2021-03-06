package com.practice.highpower.repository;

import com.practice.highpower.entity.TweetEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends ReactiveMongoRepository<TweetEntity, Long> {

}
