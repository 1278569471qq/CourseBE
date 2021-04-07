package com.zzx.coursesystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.coursesystem.dao.mapper.CourseMapper;
import com.zzx.coursesystem.dao.mapper.StudentCourseMapper;
import com.zzx.coursesystem.manager.LoginStatusManager;
import com.zzx.coursesystem.manager.student.CourseSelectManager;
import com.zzx.coursesystem.model.bo.LoginStatusBO;
import com.zzx.coursesystem.model.bo.StudentCourseSelectItemBO;
import com.zzx.coursesystem.model.vo.response.table.TeacherGradeItemVO;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzx.coursesystem.dao.ClassDAO;
import com.zzx.coursesystem.dao.CourseDAO;
import com.zzx.coursesystem.dao.DepartmentDAO;
import com.zzx.coursesystem.dao.MajorDAO;
import com.zzx.coursesystem.dao.StudentDAO;
import com.zzx.coursesystem.dao.TeacherDAO;
import com.zzx.coursesystem.model.CacheMap;
import com.zzx.coursesystem.model.entity.ClassEntity;
import com.zzx.coursesystem.model.entity.CourseEntity;
import com.zzx.coursesystem.model.entity.DepartmentEntity;
import com.zzx.coursesystem.model.entity.MajorEntity;
import com.zzx.coursesystem.model.entity.StudentEntity;
import com.zzx.coursesystem.model.entity.TeacherEntity;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.util.PinYinUtils;

/**
 * @author zhangzhenxin <zhangzhenxin@kuaishou.com>
 * Created on 2021-02-27
 */

@RestController
@EnableScheduling
public class LikeDataController  extends BaseController{
    private LoginStatusManager loginStatusManager;
    private ClassDAO classDAO;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;
    private DepartmentDAO departmentDAO;
    private MajorDAO majorDAO;
    private TeacherDAO teacherDAO;
    private CacheMap cacheMap;
    private final StudentCourseMapper mapper;
    private final CourseSelectManager courseSelectManager;
    private final HttpSession session;
    public LikeDataController(LoginStatusManager loginStatusManager, ClassDAO classDAO,
                              CourseDAO courseDAO,
                              StudentDAO studentDAO,
                              DepartmentDAO departmentDAO,
                              MajorDAO majorDAO,
                              TeacherDAO teacherDAO, StudentCourseMapper mapper, CourseMapper courseMapper, CourseSelectManager courseSelectManager, HttpSession session) {
        this.loginStatusManager = loginStatusManager;
        this.classDAO = classDAO;
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
        this.departmentDAO = departmentDAO;
        this.majorDAO = majorDAO;
        this.teacherDAO = teacherDAO;
        this.mapper = mapper;
        this.courseSelectManager = courseSelectManager;
        this.session = session;
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
    @RequestMapping("/teacher/likeData/{type}")
    public ResultVO getTeacherLikeData(@PathVariable int type) {
        Page<TeacherGradeItemVO> page = new Page<>(1, 10000);
        LoginStatusBO loginStatus = loginStatusManager.getLoginStatus(session);
        List<TeacherGradeItemVO> records = mapper.getTeacherGradePage(page, loginStatus.getUserId(), null, null).getRecords();
        switch (type) {
            case 1 :{
                Set<Map<Object, Object>> collect = records.stream()
                        .map(entity ->
                                Maps(entity.getStudentName()))
                        .collect(Collectors.toSet());
                return result(collect);
            }
            case 2 : {
                Set<Map<Object, Object>> collect = records.stream()
                        .map(entity ->
                                Maps(entity.getCourseName()))
                        .collect(Collectors.toSet());
                return result(collect);
            }
        }
        return  failedResult("false");
    }

    @RequestMapping("/student/likeData/{type}")
    public ResultVO getStudentLikeData(@PathVariable int type) {
        Page<TeacherGradeItemVO> page = new Page<>(1, 10000);
        LoginStatusBO loginStatus = loginStatusManager.getLoginStatus(session);
        List<StudentCourseSelectItemBO> selectManagerPage = courseSelectManager.getAllPage(1, loginStatus.getUserId(), null, null);
        switch (type) {
            case 1 :{
                Set<Map<Object, Object>> collect = selectManagerPage.stream()
                        .map(entity ->
                                Maps(entity.getCourseName()))
                        .collect(Collectors.toSet());
                return result(collect);
            }
            case 2 : {
                Set<Map<Object, Object>> collect = selectManagerPage.stream()
                        .map(entity ->
                                Maps(entity.getTeacherName()))
                        .collect(Collectors.toSet());
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
    @Scheduled(cron = "0/10 * * * * ? ")
    public void clearMap(){
        cacheMap.clear();
    }
}
