package com.practice.highpower.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "tweets")
public class UserEntity {

    @Id
    private String id;

    @Field(value = "name")
    private String name;


}
