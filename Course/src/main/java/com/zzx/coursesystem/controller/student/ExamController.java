package com.zzx.coursesystem.controller.student;

import com.zzx.coursesystem.config.themis.annotation.Student;
import com.zzx.coursesystem.controller.BaseController;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.student.ExamService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Student
@RequestMapping("/student/exam")
@RestController
public class ExamController extends BaseController {
    private final ExamService service;

    public ExamController(ExamService service) {
        this.service = service;
    }

    @RequestMapping("/list")
    public ResultVO list() {
        return service.list();
    }
}
