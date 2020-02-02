package com.practice.highpower.repository;

import com.practice.highpower.entity.common.SequenceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends ReactiveMongoRepository<SequenceEntity, Long> {
}
