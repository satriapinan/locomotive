package com.locomotive.reportscheduler.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "locomotive_infos")
public class InfoLokomotifModel {
    @Id
    private ObjectId id;
    @Field("Code")
    private String code;
    @Field("Name")
    private String name;
    @Field("Dimension")
    private String dimension;
    @Field("Status")
    private String status;
    @Field("DateTime")
    private String dateTime;
}
