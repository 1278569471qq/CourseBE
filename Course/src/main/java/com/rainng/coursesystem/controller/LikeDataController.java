package com.rainng.coursesystem.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rainng.coursesystem.dao.ClassDAO;
import com.rainng.coursesystem.dao.CourseDAO;
import com.rainng.coursesystem.dao.DepartmentDAO;
import com.rainng.coursesystem.dao.MajorDAO;
import com.rainng.coursesystem.dao.StudentDAO;
import com.rainng.coursesystem.dao.TeacherDAO;
import com.rainng.coursesystem.model.entity.ClassEntity;
import com.rainng.coursesystem.model.entity.CourseEntity;
import com.rainng.coursesystem.model.entity.DepartmentEntity;
import com.rainng.coursesystem.model.entity.MajorEntity;
import com.rainng.coursesystem.model.entity.StudentEntity;
import com.rainng.coursesystem.model.entity.TeacherEntity;
import com.rainng.coursesystem.model.vo.request.LikeDataVo;
import com.rainng.coursesystem.model.vo.response.ResultVO;
import com.rainng.coursesystem.util.PinYinUtils;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * @author zhangzhenxin <zhangzhenxin@kuaishou.com>
 * Created on 2021-02-27
 */

@RestController
public class LikeDataController  extends BaseController{
    private ClassDAO classDAO;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;
    private DepartmentDAO departmentDAO;
    private MajorDAO majorDAO;
    private TeacherDAO teacherDAO;

    public LikeDataController(ClassDAO classDAO,
                              CourseDAO courseDAO,
                              StudentDAO studentDAO,
                              DepartmentDAO departmentDAO,
                              MajorDAO majorDAO,
                              TeacherDAO teacherDAO) {
        this.classDAO = classDAO;
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
        this.departmentDAO = departmentDAO;
        this.majorDAO = majorDAO;
        this.teacherDAO = teacherDAO;
    }

    @RequestMapping("/likeData/{type}")
    public ResultVO getLikeData(@PathVariable int type) {
        switch (type) {
            case 1 :{
                List<MajorEntity> majorEntities = majorDAO.listName();
                return result(majorEntities.stream()
                        .map(entity -> Maps(entity.getName()))
                                .collect(Collectors.toSet()));
            }
            case 2 : {
                List<DepartmentEntity> departmentEntities = departmentDAO.listName();
                return result(departmentEntities.stream()
                        .map(entity ->
                                Maps(entity.getName()))
                        .collect(Collectors.toSet()));
            }
            case 3 : {
                List<ClassEntity> classEntities = classDAO.listName();
                return result(classEntities.stream()
                        .map(entity ->
                                Maps(entity.getName()))
                        .collect(Collectors.toSet()));
            }
            case 4 : {
                List<StudentEntity> studentEntities = studentDAO.listName();
                return result(studentEntities.stream()
                        .map(entity ->
                                Maps(entity.getName()))
                        .collect(Collectors.toSet()));
            }
            case 5 : {
                List<TeacherEntity> teacherEntities = teacherDAO.listName();
                return result(teacherEntities.stream()
                        .map(entity ->
                                Maps(entity.getName()))
                        .collect(Collectors.toSet()));
            }
            case 6 : {
                List<CourseEntity> courseEntities = courseDAO.listName();
                return result(courseEntities.stream()
                        .map(entity ->
                                Maps(entity.getName()))
                        .collect(Collectors.toSet()));
            }
        }
        return  failedResult("false");
    }

    public Map<Object, Object> Maps(String value) {
        Map<Object, Object> map = new HashMap<>();
        map.put("value", value);
        map.put("pinyin", PinYinUtils.getPinyinToLowerCase(value));
        return map;
    }
}
