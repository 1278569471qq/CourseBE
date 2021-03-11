package com.zzx.coursesystem.service.student;

import com.zzx.coursesystem.manager.student.ExamManager;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.BaseService;

import org.springframework.stereotype.Service;

@Service
public class ExamService extends BaseService {
    private final ExamManager manager;

    public ExamService(ExamManager manager) {
        this.manager = manager;
    }

    public ResultVO list() {
        return result(manager.listStudentExam(getUserId()));
    }
}
