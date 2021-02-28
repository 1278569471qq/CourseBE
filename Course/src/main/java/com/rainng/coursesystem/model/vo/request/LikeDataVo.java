package com.rainng.coursesystem.model.vo.request;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import lombok.Data;

/**
 * @author zhangzhenxin <zhangzhenxin@kuaishou.com>
 * Created on 2021-02-28
 */

@Data
public class LikeDataVo {
    String name;
    Integer type;
}
