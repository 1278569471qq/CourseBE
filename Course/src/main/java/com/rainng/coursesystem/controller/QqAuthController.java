package com.rainng.coursesystem.controller;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.rainng.coursesystem.dao.AdminDAO;
import com.rainng.coursesystem.manager.LoginStatusManager;
import com.rainng.coursesystem.manager.UserManager;
import com.rainng.coursesystem.model.bo.LoginStatusBO;
import com.rainng.coursesystem.model.entity.AdminEntity;
import com.rainng.coursesystem.model.vo.response.ResultVO;
import com.rainng.coursesystem.util.HttpUtils;

/**
 * @author zhangzhenxin <zhangzhenxin@kuaishou.com>
 * Created on 2021-02-28
 */
@CrossOrigin
@RestController
public class QqAuthController extends BaseController {
    private final HttpUtils httpUtils;
    private final HttpSession session;
    private final UserManager manager;
    private final AdminDAO adminDAO;
    private final LoginStatusManager loginStatusManager;
    private final RedisTemplate redisTemplate;
    private final String OPENID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s&fmt=json";
    private final String USER_URL = "https://graph.qq.com/user/get_user_info?access_token=%s&" +
            "oauth_consumer_key=101934691&openid=%s";

    public QqAuthController(HttpUtils httpUtils, HttpSession session, UserManager manager, AdminDAO adminDAO, LoginStatusManager loginStatusManager, RedisTemplate redisTemplate) {
        this.httpUtils = httpUtils;
        this.session = session;
        this.manager = manager;
        this.adminDAO = adminDAO;
        this.loginStatusManager = loginStatusManager;
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping("/auth")
    public ResultVO qqAuth(@RequestParam("token")String token) {
        try {
            if (StringUtils.isEmpty(token) || token.length() < 8) {
                return result("失败");
            }
            session.setAttribute("token",token);
            String openId = getOpenId(token);
            LoginStatusBO loginStatus = loginStatusManager.getLoginStatus(session);
            if (loginStatus.getLoggedIn()) {
                redisTemplate.opsForValue().set(openId, loginStatus);
                return result("绑定成功");
            } else {
                loginStatus = (LoginStatusBO) redisTemplate.opsForValue().get(openId);
                if (loginStatus == null || !loginStatus.getLoggedIn()) {
                   return failedResult("QQ账号没有绑定系统号，请绑定后在登陆");
                }
                loginStatusManager.setLoginStatus(session, loginStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result("成功");
    }
    public String getOpenId(String token) {
        String url = String.format(OPENID_URL, token);
        JSONObject response = httpUtils.sendGetRequest(url);
        String openid = response.getString("openid");
        return openid;
    }

    public JSONObject getInfo(String token){
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String openId = getOpenId(token);
        String userUrl = String.format(USER_URL, token, openId);
        return httpUtils.sendGetRequest(userUrl);
    }
    @RequestMapping("/qq/author/url")
    public ResultVO getAuthorUrl() {
        String token = (String) session.getAttribute("token");
        if (StringUtils.isEmpty(token)) {
            return failedResult("false");
        }
        JSONObject info = getInfo(token);
        return result(info.get("figureurl_qq_1"));
    }
}
