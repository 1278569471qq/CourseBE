package com.zzx.coursesystem.manager.student;

import com.zzx.coursesystem.dao.StudentDAO;
import com.zzx.coursesystem.manager.BaseManager;
import com.zzx.coursesystem.model.entity.StudentEntity;
import com.zzx.coursesystem.model.vo.response.StudentInfoVO;

import org.springframework.stereotype.Component;

@Component
public class InfoManager extends BaseManager {
    private final StudentDAO studentDAO;

    public InfoManager(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public StudentInfoVO getStudentInfoByStudentId(Integer studentId) {
        return studentDAO.getStudentInfoById(studentId);
    }

    public StudentEntity getStudentById(Integer studentId) {
        return studentDAO.get(studentId);
    }

    public int updateStudent(StudentEntity entity) {
        return studentDAO.update(entity);
    }
}
