package com.zzx.coursesystem.controller;

import java.util.List;

import com.zzx.coursesystem.dao.mongo.LogDAO;
import com.zzx.coursesystem.model.entity.mongo.LogEntity;
import com.zzx.coursesystem.model.vo.request.LoginVO;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.UserService;

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

    public UserController(UserService service, LogDAO logDAO) {
        this.service = service;
        this.logDAO = logDAO;
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


    @RequestMapping("/log/{page}")
    public ResultVO logout(@PathVariable("page") Integer Page) {
        org.springframework.data.domain.Page<LogEntity> logAll = logDAO.getAll();
        List<LogEntity> content = logAll.getContent();
            System.out.println(logAll.getTotalPages());
        return result(content);
    }

}
