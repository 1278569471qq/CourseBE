package com.zzx.coursesystem.controller.teacher;

import com.zzx.coursesystem.config.themis.annotation.Teacher;
import com.zzx.coursesystem.controller.BaseController;
import com.zzx.coursesystem.manager.OptionManager;
import com.zzx.coursesystem.model.vo.TeacherGradeVO;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.teacher.GradeService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Teacher
@RequestMapping("/teacher/grade")
@RestController
public class GradeController extends BaseController {
    private final GradeService service;
    private final OptionManager optionManager;

    public GradeController(GradeService service, OptionManager optionManager) {
        this.service = service;
        this.optionManager = optionManager;
    }

    @RequestMapping("/page/count")
    public ResultVO getPageCount(String courseName, String studentName) {
        return service.getPageCount(courseName, studentName);
    }

    @RequestMapping("/page/{index}")
    public ResultVO getPage(@PathVariable Integer index, String courseName, String studentName) {
        return service.getPage(index, courseName, studentName);
    }

    @GetMapping("/{id}")
    public ResultVO get(@PathVariable Integer id) {
        return service.get(id);
    }

    @PutMapping
    public ResultVO update(@RequestBody @Validated TeacherGradeVO vo) {
        return service.update(vo);
    }

    @GetMapping("/allow")
    public ResultVO allow(){
        if (!optionManager.getAllowTeacherGrade()) {
            return result(false);
        }
        return result(true);
    }
}
