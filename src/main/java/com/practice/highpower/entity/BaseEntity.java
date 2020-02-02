package com.practice.highpower.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity {

    @Field(value = "updated_by")
    private String updatedBy = "God?";

    @Field(value = "created_by")
    private String createdBy = "God?";

    @Field(value = "created_at")
    @CreatedDate
    private Date createdAt;

    @Field(value = "updated_at")
    @LastModifiedDate
    private Date updatedAt;
}
