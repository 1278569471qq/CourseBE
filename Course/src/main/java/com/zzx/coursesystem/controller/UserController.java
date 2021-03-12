package com.zzx.coursesystem.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.zzx.coursesystem.config.themis.annotation.Admin;
import com.zzx.coursesystem.dao.mongo.LogDAO;
import com.zzx.coursesystem.model.entity.mongo.LogEntity;
import com.zzx.coursesystem.model.vo.request.LoginVO;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.UserService;
import com.zzx.coursesystem.util.WebSocketUserUtils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController extends BaseController {
    private final UserService service;

    private final LogDAO logDAO;

    private final WebSocketUserUtils webSocketUserUtils;

    private final RedisTemplate redisTemplate;
    public UserController(UserService service, LogDAO logDAO, WebSocketUserUtils webSocketUserUtils, RedisTemplate redisTemplate) {
        this.service = service;
        this.logDAO = logDAO;
        this.webSocketUserUtils = webSocketUserUtils;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/login")
    public ResultVO login(@Validated @RequestBody LoginVO loginVO) {
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();
        Integer userType = loginVO.getUserType();
        return service.login(username, password, userType);
    }

    @RequestMapping("/login/status")
    public ResultVO getLoginStatus() {
        return service.getLoginStatus();
    }

    @RequestMapping("/logout")
    public ResultVO logout() {
        return service.logout();
    }

    @Admin
    @RequestMapping("/log/{page}")
    public ResultVO getLog(@PathVariable("page") Integer Page) {
        org.springframework.data.domain.Page<LogEntity> logAll = logDAO.getAll();
        List<LogEntity> content = logAll.getContent();
        return result(content);
    }

    @Admin
    @RequestMapping("/current/user")
    public ResultVO getCurUsers() {

        Map curUsers = webSocketUserUtils.getCurUsers();
        List<LogEntity> users = (List<LogEntity>) curUsers.get("users");
        Set<Object> result = users.stream().map(user -> {
            String userName = user.getUserName();
            String json = (String) redisTemplate.opsForHash().get("CURRENT_USER_MAP", userName);
            if (json != null) {
                return JSON.parse(json);
            }
            return user;
        }).collect(Collectors.toSet());
        curUsers.put("users", result);
        return result(curUsers);
    }
}
