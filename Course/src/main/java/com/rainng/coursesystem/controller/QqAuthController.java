package com.rainng.coursesystem.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangzhenxin <zhangzhenxin@kuaishou.com>
 * Created on 2021-02-28
 */
@RestController
public class QqAuthController {

    @RequestMapping("/auth")
    public void qqAuth(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("www.zzxblog.top");
        } catch (Exception e) {

        }finally {
            System.out.println("---------ok");
        }
    }

}
