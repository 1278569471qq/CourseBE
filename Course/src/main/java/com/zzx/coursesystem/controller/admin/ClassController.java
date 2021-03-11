package com.zzx.coursesystem.controller.admin;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzx.coursesystem.config.themis.annotation.Admin;
import com.zzx.coursesystem.controller.BaseController;
import com.zzx.coursesystem.model.entity.ClassEntity;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.admin.ClassService;

@Admin(Admin.CLASS_MANAGE)
@RequestMapping("/admin/class")
@RestController
public class ClassController extends BaseController {
    private final ClassService service;

    public ClassController(ClassService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResultVO get(@PathVariable Integer id) {
        return service.get(id);
    }

    @PostMapping
    public ResultVO create(@RequestBody @Validated ClassEntity entity) {
        return service.create(entity);
    }

    @DeleteMapping("/{id}")
    public ResultVO delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @PutMapping
    public ResultVO update(@RequestBody @Validated ClassEntity entity) {
        return service.update(entity);
    }

    @RequestMapping("/page/count")
    public ResultVO getPageCount(String departmentName, String majorName, String name) {
        return service.getPageCount(departmentName, majorName, name);
    }

    @RequestMapping("/page")
    public ResultVO getPage(String departmentName, String majorName, String name) {
        return service.getPage(1, departmentName, majorName, name);
    }

    @RequestMapping("/page/{index}")
    public ResultVO getPage(@PathVariable Integer index, String departmentName, String majorName, String name) {
        return service.getPage(index, departmentName, majorName, name);
    }

    @Admin
    @RequestMapping("/names")
    public ResultVO listName() {
        return service.listName();
    }
}
