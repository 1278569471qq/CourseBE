package com.zzx.coursesystem.dao.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.zzx.coursesystem.model.entity.mongo.LogEntity;

@Repository
public class LogDAO {
    private final LogRepository repository;

    public LogDAO(LogRepository repository) {
        this.repository = repository;
    }

    public void insert(LogEntity entity) {
        if (entity == null) {
            return;
        }
        if (entity.getResultCode() == null || entity.getRequestUrl() == null) {
            return;
        }
        if (entity.getRequestUrl().indexOf("/user/log") >= 0) {
            return;
        }
        repository.insert(entity);
    }
    public Page<LogEntity> getAll() {
        Sort datetime = Sort.by(Sort.Direction.DESC, "datetime");
        PageRequest pageRequest = PageRequest.of(1, 20, datetime);
        return repository.findAll(pageRequest);
    }
}
