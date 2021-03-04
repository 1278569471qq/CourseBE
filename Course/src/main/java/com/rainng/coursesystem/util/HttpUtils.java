package com.rainng.coursesystem.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zhangzhenxin <zhangzhenxin@kuaishou.com>
 * Created on 2021-02-28
 */
@Component
public class HttpUtils {
    @Autowired
    private RestTemplate restTemplate;
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
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
}
