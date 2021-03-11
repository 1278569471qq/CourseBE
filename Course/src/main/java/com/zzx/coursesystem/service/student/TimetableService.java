package com.zzx.coursesystem.service.student;

import com.zzx.coursesystem.manager.student.TimetableManager;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.BaseService;

import org.springframework.stereotype.Service;

@Service("student_timetableService")
public class TimetableService extends BaseService {
    private final TimetableManager manager;

    public TimetableService(TimetableManager manager) {
        this.manager = manager;
    }

    public ResultVO get() {
        Integer studentId = getUserId();
        return result(manager.listStudentTimetable(studentId));
    }
}
