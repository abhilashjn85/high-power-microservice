package com.practice.highpower.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@ToString
@Document(collection = "tweets")
@Builder
public class TweetEntity extends BaseEntity {

    @Id
    private Long id;


    @Transient
    public static final String SEQUENCE_NAME = "tweet_sequence";

    @NotBlank
    @Size(max = 150)
    @Field(value = "tweet")
    @NotNull
    private String tweet;

    @Field(value = "hash_tag")
    private String hashTag;

    @Field(value = "location")
    private Object location;

    public TweetEntity(String tweet) {
        this.tweet =  tweet;
    }

    public TweetEntity(String tweet, String hashTag) {
        this.tweet =  tweet;
        this.hashTag = hashTag;
    }

    public TweetEntity(Long id, String tweet) {
        this.id = id;
        this.tweet = tweet;
    }
}
