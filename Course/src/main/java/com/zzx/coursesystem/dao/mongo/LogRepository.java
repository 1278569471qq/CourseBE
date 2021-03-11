package com.zzx.coursesystem.dao.mongo;

import com.zzx.coursesystem.model.entity.mongo.LogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<LogEntity, Long> {
}
