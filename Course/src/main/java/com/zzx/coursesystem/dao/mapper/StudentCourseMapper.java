package com.zzx.coursesystem.dao.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zzx.coursesystem.model.entity.StudentCourseEntity;
import com.zzx.coursesystem.model.vo.response.table.StudentCourseItemVO;
import com.zzx.coursesystem.model.vo.response.table.StudentCourseSelectedItemVO;
import com.zzx.coursesystem.model.vo.response.table.StudentExamItemVO;
import com.zzx.coursesystem.model.vo.response.table.TeacherGradeItemVO;
import com.zzx.coursesystem.model.vo.response.table.TimetableItemVO;

@Repository
public interface StudentCourseMapper extends BaseMapper<StudentCourseEntity> {

    Integer count(String className, String courseName, String studentName);

    IPage<StudentCourseItemVO> getPage(IPage<StudentCourseItemVO> page, String className, String courseName, String studentName);

    Integer countTeacherGrade(Integer teacherId, String courseName, String studentName);

    IPage<TeacherGradeItemVO> getTeacherGradePage(IPage<TeacherGradeItemVO> page, Integer teacherId, String courseName, String studentName);

    List<StudentCourseSelectedItemVO> listStudentCourseSelected(Integer studentId);

    List<StudentExamItemVO> listStudentExam(Integer studentId);

    Integer countStudentCourseSelectedByTimePart(Integer studentId, String timePart);

    List<TimetableItemVO> listStudentTimetable(Integer studentId);
}
