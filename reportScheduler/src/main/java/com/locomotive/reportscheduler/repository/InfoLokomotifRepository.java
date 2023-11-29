package com.locomotive.reportscheduler.repository;

import com.locomotive.reportscheduler.model.InfoLokomotifModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InfoLokomotifRepository extends MongoRepository<InfoLokomotifModel, ObjectId> {
}

