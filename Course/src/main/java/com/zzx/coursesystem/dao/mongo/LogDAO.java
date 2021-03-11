package com.zzx.coursesystem.dao.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.zzx.coursesystem.model.entity.mongo.LogEntity;
import com.zzx.coursesystem.util.WebSocketUtils;

@Repository
public class LogDAO {
    private final LogRepository repository;
    private final WebSocketUtils webSocketUtils;
    public LogDAO(LogRepository repository, WebSocketUtils webSocketUtils) {
        this.repository = repository;
        this.webSocketUtils = webSocketUtils;
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

        webSocketUtils.sendAllInfo(JSON.toJSONString(entity));
    }
    public Page<LogEntity> getAll() {
        Sort datetime = Sort.by(Sort.Direction.DESC, "datetime");
        PageRequest pageRequest = PageRequest.of(0, 200, datetime);
        return repository.findAll(pageRequest);
    }
}
