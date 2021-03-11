package com.zzx.coursesystem.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zhangzhenxin <zhangzhenxin@kuaishou.com>
 * Created on 2021-02-28
 */
@Component
public class HttpUtils {
    private final static String location_url = "http://api.map.baidu.com/location/ip?ak=Ni1j0qWt2Xan5XLm6RVwSC2V9iy1lfSW&ip=%s";

    @Autowired
    private RestTemplate restTemplate;
    @Bean
    private RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    public JSONObject sendGetRequest(String url) {
        String msg = restTemplate.getForObject(url, String.class);
        return JSON.parseObject(msg);
    }

    public JSONObject sendPostRequest(String url, String body) {
        restTemplate.postForObject(url, body, String.class);
        return null;
    }

    public JSONObject getLocation(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return new JSONObject();
        }
        String url = String.format(location_url, ip);
        String msg = restTemplate.getForObject(url, String.class);
        System.out.println(msg);
        return JSON.parseObject(msg);
    }
}
