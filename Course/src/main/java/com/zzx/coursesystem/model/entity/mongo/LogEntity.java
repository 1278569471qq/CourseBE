package com.zzx.coursesystem.model.entity.mongo;

import lombok.Data;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document("request-log")
@Data
public class LogEntity {
    private Integer userId;
    private String  userName;
    private Integer userType;
    private String requestUrl;
    private String businessTarget;
    private String message;
    private Object exception;
    private String ip;
    private String location;
    private Integer resultCode;
    private Long executeTime;

    @Indexed(expireAfterSeconds = 864000) //10天
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") //出参
    private Date datetime = new Date();
}
