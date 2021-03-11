package com.zzx.coursesystem.controller.student;

import com.zzx.coursesystem.config.themis.annotation.Student;
import com.zzx.coursesystem.controller.BaseController;
import com.zzx.coursesystem.model.vo.request.StudentInfoFormVO;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.student.InfoService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Student
@RequestMapping("/student/info")
@RestController
public class InfoController extends BaseController {
    private final InfoService service;

    public InfoController(InfoService service) {
        this.service = service;
    }

    @GetMapping
    public ResultVO get() {
        return service.get();
    }

    @PutMapping
    public ResultVO update(@RequestBody @Validated StudentInfoFormVO formVO) {
        return service.update(formVO);
    }
}
