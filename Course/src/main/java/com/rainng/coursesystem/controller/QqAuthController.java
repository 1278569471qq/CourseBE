package com.rainng.coursesystem.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qq.connect.oauth.Oauth;

/**
 * @author zhangzhenxin <zhangzhenxin@kuaishou.com>
 * Created on 2021-02-28
 */
@RestController
public class QqAuthController {

    @RequestMapping("/qqAuth")
    public void qqAuth(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println("-------------");
            String authorizeURL = new Oauth().getAuthorizeURL(request);
            response.sendRedirect(authorizeURL);
        } catch (Exception e) {

        }
    }

    @RequestMapping("/qqLoginBack")
    public void qqLoginBack(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        try {
            System.out.println("----------");
            response.sendRedirect("www.zzxblog.top");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
