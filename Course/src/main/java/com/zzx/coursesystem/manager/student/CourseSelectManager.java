package com.zzx.coursesystem.manager.student;

import com.zzx.coursesystem.dao.CourseDAO;
import com.zzx.coursesystem.dao.StudentCourseDAO;
import com.zzx.coursesystem.dao.StudentDAO;
import com.zzx.coursesystem.manager.BaseManager;
import com.zzx.coursesystem.model.bo.StudentCourseSelectItemBO;
import com.zzx.coursesystem.model.entity.CourseEntity;
import com.zzx.coursesystem.model.entity.StudentCourseEntity;
import com.zzx.coursesystem.model.entity.StudentEntity;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CourseSelectManager extends BaseManager {
    private final CourseDAO courseDAO;
    private final StudentDAO studentDAO;
    private final StudentCourseDAO studentCourseDAO;

    public CourseSelectManager(CourseDAO courseDAO, StudentDAO studentDAO, StudentCourseDAO studentCourseDAO) {
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
        this.studentCourseDAO = studentCourseDAO;
    }

    public Integer getPageCount(Integer studentId, String courseName, String teacherName) {
        Integer departmentId = studentDAO.getDepartmentIdById(studentId);
        Integer grade = studentDAO.getGradeById(studentId);
        return calcPageCount(courseDAO.countStudentCanSelect(departmentId, studentId, grade, courseName, teacherName), StudentCourseDAO.PAGE_SIZE);
    }

    public List<StudentCourseSelectItemBO> getPage(Integer index, Integer studentId, String courseName, String teacherName) {
        Integer departmentId = studentDAO.getDepartmentIdById(studentId);
        Integer grade = studentDAO.getGradeById(studentId);
        List<StudentCourseSelectItemBO> studentCanSelectPage = courseDAO.getStudentCanSelectPage(index, studentId, departmentId, grade, courseName, teacherName);
        return studentCanSelectPage;
    }
    public List<StudentCourseSelectItemBO> getAllPage(Integer index, Integer studentId, String courseName, String teacherName) {
        Integer departmentId = studentDAO.getDepartmentIdById(studentId);
        Integer grade = studentDAO.getGradeById(studentId);
        List<StudentCourseSelectItemBO> studentCanSelectPage = courseDAO.getAllStudentCanSelectPage(index, studentId, departmentId, grade, courseName, teacherName);
        return studentCanSelectPage;
    }

    public CourseEntity getCourseById(Integer courseId) {
        return courseDAO.get(courseId);
    }

    public StudentEntity getStudentById(Integer studentId) {
        return studentDAO.get(studentId);
    }

    public boolean inSameDepartment(Integer courseId, Integer studentId) {
        return courseDAO.getDepartmentIdById(courseId)
                .equals(studentDAO.getDepartmentIdById(studentId));
    }

    public StudentCourseEntity getStudentCourseByCourseIdAndStudentId(Integer courseId, Integer studentId) {
        return studentCourseDAO.getByCourseIdAndStudentId(courseId, studentId);
    }

    public Integer getStudentGradeById(Integer studentId) {
        return studentDAO.getGradeById(studentId);
    }

    @Transactional
    public int create(StudentCourseEntity entity) {
        courseDAO.increaseSelectedCount(entity.getCourseId());
        return studentCourseDAO.insert(entity);
    }

    public int countStudentCourseSelectedByTimePart(Integer studentId, String timePart) {
        return studentCourseDAO.countStudentCourseSelectedByTimePart(studentId, timePart);
    }
}
