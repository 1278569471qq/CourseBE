package com.zzx.coursesystem.manager.student;

import com.zzx.coursesystem.dao.StudentCourseDAO;
import com.zzx.coursesystem.manager.BaseManager;
import com.zzx.coursesystem.model.vo.response.table.TimetableItemVO;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("student_timetableManager")
public class TimetableManager extends BaseManager {
    private final StudentCourseDAO studentCourseDAO;

    public TimetableManager(StudentCourseDAO studentCourseDAO) {
        this.studentCourseDAO = studentCourseDAO;
    }

    public List<TimetableItemVO> listStudentTimetable(Integer studentId) {
        return studentCourseDAO.listStudentTimetable(studentId);
    }
}
