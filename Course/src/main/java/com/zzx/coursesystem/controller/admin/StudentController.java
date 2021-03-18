package com.zzx.coursesystem.controller.admin;

import com.zzx.coursesystem.config.themis.annotation.Admin;
import com.zzx.coursesystem.controller.BaseController;
import com.zzx.coursesystem.model.entity.StudentEntity;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.admin.StudentService;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Admin(Admin.STUDENT_MANAGE)
@RequestMapping("/admin/student")
@RestController
public class StudentController extends BaseController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResultVO get(@PathVariable Integer id) {
        return service.get(id);
    }

    @PostMapping
    public ResultVO create(@RequestBody @Validated StudentEntity entity) {
        return service.create(entity);
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @PutMapping
    public ResultVO update(@RequestBody @Validated StudentEntity entity) {
        return service.update(entity);
    }

    @RequestMapping("/page/count")
    public ResultVO getPageCount(String majorName, String className, String name) {
        return service.getPageCount(majorName, className, name);
    }

    @RequestMapping("/page")
    public ResultVO getPage(String majorName, String className, String name) {
        return service.getPage(1, majorName, className, name);
    }

    @RequestMapping("/page/{index}")
    public ResultVO getPage(@PathVariable Integer index, String majorName, String className, String name) {
        return service.getPage(index, majorName, className, name);
    }

    @Admin
    @RequestMapping("/names")
    public ResultVO listName() {
        return service.listName();
    }

    @RequestMapping("/upload")
    public ResultVO upload(@RequestParam(value = "file") MultipartFile file){
        if (file == null) {
            return failedResult("文件不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.endsWithIgnoreCase(originalFilename, "xlsx")) {
            return failedResult("文件只支持xlsx");
        }
        return result("success");
    }

}
