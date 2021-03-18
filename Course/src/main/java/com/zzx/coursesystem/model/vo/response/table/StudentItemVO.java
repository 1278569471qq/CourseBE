package com.zzx.coursesystem.model.vo.response.table;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class StudentItemVO {
    private Integer id;
    private String number;
    private String name;
    private String className;
    private String majorName;
    private Integer sex;
    private Long score;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date lastLoginTime;
}
