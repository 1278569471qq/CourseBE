package com.rainng.coursesystem.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * @author zhangzhenxin <zhangzhenxin@kuaishou.com>
 * Created on 2021-02-28
 */
@Component
public  class CacheMap {
    private Map<String, Object> map;
    public CacheMap bulid(){
        map = new ConcurrentHashMap<>();
        return this;
    }
    public CacheMap bulid(int initialCapacity){
        map = new ConcurrentHashMap<>(initialCapacity);
        return this;
    }
    public Object put(String key, Object value){
        map.put(key, value);
        return this;
    }
    public Object get(String key){
        return map.get(key);
    }

    public void clear() {
        map.clear();
    }
}
