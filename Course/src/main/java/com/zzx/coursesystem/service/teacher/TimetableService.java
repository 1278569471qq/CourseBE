package com.zzx.coursesystem.service.teacher;

import com.zzx.coursesystem.manager.teacher.TimetableManager;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.BaseService;

import org.springframework.stereotype.Service;

@Service
public class TimetableService extends BaseService {
    private final TimetableManager manager;

    public TimetableService(TimetableManager manager) {
        this.manager = manager;
    }

    public ResultVO get() {
        Integer teacherId = getUserId();
        return result(manager.listTeacherTimetable(teacherId));
    }
}
