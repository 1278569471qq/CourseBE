package com.zzx.coursesystem.manager;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.zzx.coursesystem.model.bo.LoginStatusBO;

@Component
public class LoginStatusManager extends BaseManager {
    private static final String SESSION_USER_STATUS = "user_status";

    public void setLoginStatus(HttpSession session, LoginStatusBO loginStatus) {
        session.setAttribute(SESSION_USER_STATUS, loginStatus);
    }

    public LoginStatusBO getLoginStatus(HttpSession session) {
        LoginStatusBO loginStatus = (LoginStatusBO) session.getAttribute(SESSION_USER_STATUS);
        if (loginStatus == null) {
            loginStatus = new LoginStatusBO();
            setLoginStatus(session, loginStatus);
        }

        return loginStatus;
    }
}
