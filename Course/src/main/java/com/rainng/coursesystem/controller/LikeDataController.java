package com.rainng.coursesystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rainng.coursesystem.dao.ClassDAO;
import com.rainng.coursesystem.dao.CourseDAO;
import com.rainng.coursesystem.dao.DepartmentDAO;
import com.rainng.coursesystem.dao.MajorDAO;
import com.rainng.coursesystem.dao.StudentDAO;
import com.rainng.coursesystem.dao.TeacherDAO;
import com.rainng.coursesystem.model.CacheMap;
import com.rainng.coursesystem.model.entity.ClassEntity;
import com.rainng.coursesystem.model.entity.CourseEntity;
import com.rainng.coursesystem.model.entity.DepartmentEntity;
import com.rainng.coursesystem.model.entity.MajorEntity;
import com.rainng.coursesystem.model.entity.StudentEntity;
import com.rainng.coursesystem.model.entity.TeacherEntity;
import com.rainng.coursesystem.model.vo.response.ResultVO;
import com.rainng.coursesystem.util.PinYinUtils;

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
    private CacheMap cacheMap;

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
    @PostConstruct
    public void init(){
        CacheMap cacheMap = new CacheMap();
        this.cacheMap = cacheMap.bulid();
    }

    @RequestMapping("/likeData/{type}")
    public ResultVO getLikeData(@PathVariable int type) {
        switch (type) {
            case 1 :{
                if (cacheMap.get(type + "") != null) {
                    return result(cacheMap.get(type + ""));
                }
                List<MajorEntity> majorEntities = majorDAO.listName();
                Set<Map<Object, Object>> collect = majorEntities.stream()
                        .map(entity -> Maps(entity.getName()))
                        .collect(Collectors.toSet());
                cacheMap.put(type + "", collect);
                return result(collect);
            }
            case 2 : {
                if (cacheMap.get(type + "") != null) {
                    return result(cacheMap.get(type + ""));
                }
                List<DepartmentEntity> departmentEntities = departmentDAO.listName();
                Set<Map<Object, Object>> collect = departmentEntities.stream()
                        .map(entity ->
                                Maps(entity.getName()))
                        .collect(Collectors.toSet());
                cacheMap.put(type + "", collect);
                return result(collect);
            }
            case 3 : {
                if (cacheMap.get(type + "") != null) {
                    return result(cacheMap.get(type + ""));
                }
                List<ClassEntity> classEntities = classDAO.listName();
                Set<Map<Object, Object>> collect = classEntities.stream()
                        .map(entity ->
                                Maps(entity.getName()))
                        .collect(Collectors.toSet());
                cacheMap.put(type + "", collect);
                return result(collect);
            }
            case 4 : {
                if (cacheMap.get(type + "") != null) {
                    return result(cacheMap.get(type + ""));
                }
                List<StudentEntity> studentEntities = studentDAO.listName();
                Set<Map<Object, Object>> collect = studentEntities.stream()
                        .map(entity ->
                                Maps(entity.getName()))
                        .collect(Collectors.toSet());
                cacheMap.put(type + "", collect);
                return result(collect);
            }
            case 5 : {
                if (cacheMap.get(type + "") != null) {
                    return result(cacheMap.get(type + ""));
                }
                List<TeacherEntity> teacherEntities = teacherDAO.listName();
                Set<Map<Object, Object>> collect = teacherEntities.stream()
                        .map(entity ->
                                Maps(entity.getName()))
                        .collect(Collectors.toSet());
                cacheMap.put(type + "", collect);
                return result(collect);
            }
            case 6 : {
                if (cacheMap.get(type + "") != null) {
                    return result(cacheMap.get(type + ""));
                }
                List<CourseEntity> courseEntities = courseDAO.listName();
                Set<Map<Object, Object>> collect = courseEntities.stream()
                        .map(entity ->
                                Maps(entity.getName()))
                        .collect(Collectors.toSet());
                cacheMap.put(type + "", collect);
                return result(collect);
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
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void clearMap(){
        cacheMap.clear();
    }
}
