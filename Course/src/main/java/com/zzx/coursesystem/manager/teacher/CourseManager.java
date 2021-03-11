package com.zzx.coursesystem.manager.teacher;

import com.zzx.coursesystem.dao.TeacherDAO;
import com.zzx.coursesystem.manager.BaseManager;
import com.zzx.coursesystem.model.vo.response.table.TeacherCourseItemVO;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("teacher_CourseManager")
public class CourseManager extends BaseManager {
    private final TeacherDAO teacherDAO;

    public CourseManager(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    public List<TeacherCourseItemVO> listTeacherCourse(Integer teacherId) {
        return teacherDAO.listTeacherCourse(teacherId);
    }
}
