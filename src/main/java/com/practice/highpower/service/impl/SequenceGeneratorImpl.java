package com.practice.highpower.service.impl;

import com.practice.highpower.entity.common.SequenceEntity;
import com.practice.highpower.exception.SequenceException;
import com.practice.highpower.service.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorImpl implements SequenceGenerator {

    @Autowired
    private MongoOperations mongoOperation;

    @Override
    public long getNextSequenceId(String key) throws SequenceException {

        Query query = new Query(Criteria.where("_id").is(key));
        Update update = new Update();
        update.inc("seq", 1);

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        SequenceEntity seqId =
                mongoOperation.findAndModify(query, update, options, SequenceEntity.class);

        if (seqId == null) {
            throw new SequenceException("Unable to get sequence id for key : " + key);
        }

        return seqId.getSeq();

    }
}
