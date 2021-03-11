package com.zzx.coursesystem.controller.sdk;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zzx.coursesystem.config.themis.annotation.Admin;
import com.zzx.coursesystem.controller.BaseController;
import com.zzx.coursesystem.util.HttpUtils;

/**
 * @author zhangzhenxin <zhangzhenxin@kuaishou.com>
 * Created on 2021-03-11
 */
@CrossOrigin
@RestController
@Admin(Admin.ADMIN_MANAGE)
public class BaiduLocationController  extends BaseController {
    private final static String location_url = "http://api.map.baidu.com/location/ip?ak=Ni1j0qWt2Xan5XLm6RVwSC2V9iy1lfSW&ip=%s";
    private final HttpUtils httpUtils;
    public BaiduLocationController(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    @RequestMapping("/location")
    public void getLocation(String ip){
        if (StringUtils.isEmpty(ip)) {
            return;
        }
        String url = String.format(location_url, ip);
        JSONObject result = httpUtils.sendGetRequest(url);
        System.out.println(result.toString());
    }
}
