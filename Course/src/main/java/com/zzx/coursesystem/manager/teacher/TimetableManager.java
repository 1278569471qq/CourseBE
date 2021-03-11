package com.zzx.coursesystem.manager.teacher;

import com.zzx.coursesystem.dao.TeacherDAO;
import com.zzx.coursesystem.manager.BaseManager;
import com.zzx.coursesystem.model.vo.response.table.TimetableItemVO;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimetableManager extends BaseManager {
    private final TeacherDAO teacherDAO;

    public TimetableManager(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    public List<TimetableItemVO> listTeacherTimetable(Integer teacherId) {
        return teacherDAO.listTeacherTimetable(teacherId);
    }
}
