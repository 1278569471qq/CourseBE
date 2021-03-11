package com.zzx.coursesystem.controller.student;

import com.zzx.coursesystem.config.themis.annotation.Student;
import com.zzx.coursesystem.controller.BaseController;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.student.TimetableService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Student
@RequestMapping("/student/timetable")
@RestController("student_timeTableController")
public class TimetableController extends BaseController {
    private final TimetableService service;

    public TimetableController(TimetableService service) {
        this.service = service;
    }

    @RequestMapping
    public ResultVO get() {
        return service.get();
    }
}
