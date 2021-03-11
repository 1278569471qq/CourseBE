package com.zzx.coursesystem.controller.student;

import com.zzx.coursesystem.config.themis.annotation.Student;
import com.zzx.coursesystem.controller.BaseController;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.student.CourseService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Student
@RequestMapping("/student/course")
@RestController("student_courseController")
public class CourseController extends BaseController {
    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @RequestMapping("/list")
    public ResultVO list() {
        return service.list();
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable Integer id) {
        return service.delete(id);
    }
}
