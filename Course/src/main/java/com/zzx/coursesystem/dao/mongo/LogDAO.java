package com.zzx.coursesystem.dao.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.zzx.coursesystem.model.entity.mongo.LogEntity;
import com.zzx.coursesystem.util.WebSocketUtils;

@Repository
public class LogDAO {
    private final LogRepository repository;
    private final WebSocketUtils webSocketUtils;
    private final RedisTemplate redisTemplate;

    public LogDAO(LogRepository repository, WebSocketUtils webSocketUtils, RedisTemplate redisTemplate) {
        this.repository = repository;
        this.webSocketUtils = webSocketUtils;
        this.redisTemplate = redisTemplate;
    }

    public void insert(LogEntity entity) {
        if (entity == null) {
            return;
        }
        if (entity.getResultCode() == null || StringUtils.isEmpty(entity.getRequestUrl())) {
            return;
        }
        if (entity.getRequestUrl().indexOf("/user/log") >= 0 || entity.getRequestUrl().indexOf("/current/user") >= 0) {
            return;
        }
        repository.insert(entity);
        String userName = entity.getUserName();
        if (!StringUtils.isEmpty(userName)) {
            redisTemplate.opsForHash().put("CURRENT_USER_MAP", userName, JSON.toJSONString(entity));
        }
        webSocketUtils.sendAllInfo(JSON.toJSONString(entity));

    }
    public Page<LogEntity> getAll() {
        Sort datetime = Sort.by(Sort.Direction.DESC, "datetime");
        PageRequest pageRequest = PageRequest.of(0, 200, datetime);
        return repository.findAll(pageRequest);
    }
}
