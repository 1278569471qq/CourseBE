package com.rainng.coursesystem.controller;

import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.rainng.coursesystem.manager.LoginStatusManager;
import com.rainng.coursesystem.manager.UserManager;
import com.rainng.coursesystem.model.bo.AuthInfoBO;
import com.rainng.coursesystem.model.bo.LoginStatusBO;
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
    private final LoginStatusManager loginStatusManager;
    private final String OPENID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s&fmt=json";
    private final String USER_URL = "https://graph.qq.com/user/get_user_info?access_token=%s&" +
            "oauth_consumer_key=101934691&openid=%s";
    public QqAuthController(HttpUtils httpUtils, HttpSession session, UserManager manager, LoginStatusManager loginStatusManager) {
        this.httpUtils = httpUtils;
        this.session = session;
        this.manager = manager;
        this.loginStatusManager = loginStatusManager;
    }

    @RequestMapping("/auth")
    public ResultVO qqAuth(@RequestParam("token")String token) {
        if (StringUtils.isEmpty(token)) {
            return failedResult("失败");
        }
        try {
            String url = String.format(OPENID_URL, token);
            System.out.println(url);
            JSONObject response = httpUtils.sendGetRequest(url);
            System.out.println(response.toString());
            String infoUrl = String.format(USER_URL, token, response.getString("openid"));
            JSONObject userInfo = httpUtils.sendGetRequest(infoUrl);
            System.out.println(userInfo.toString());
//            AuthInfoBO authInfo = manager.getAuthInfoByUsername(username, userType);
            AuthInfoBO authInfo = manager.getAuthInfoByUsername("zzx", 3);
            LoginStatusBO statusBO = LoginStatusBO.fromAuthInfo(authInfo);
            loginStatusManager.setLoginStatus(session, statusBO);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return result(token);
    }

}
