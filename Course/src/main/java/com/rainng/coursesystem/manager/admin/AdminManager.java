package com.rainng.coursesystem.manager.admin;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rainng.coursesystem.dao.AdminDAO;
import com.rainng.coursesystem.manager.BaseManager;
import com.rainng.coursesystem.model.entity.AdminEntity;

@Component
public class AdminManager extends BaseManager {
    private final AdminDAO adminDAO;

    public AdminManager(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public AdminEntity get(Integer id) {
        return adminDAO.get(id);
    }

    public int create(AdminEntity entity) {
        return adminDAO.insert(entity);
    }

    public int update(AdminEntity entity) {
        return adminDAO.update(entity);
    }

    public int delete(Integer id) {
        return adminDAO.delete(id);
    }

    public List<AdminEntity> list() {
        return adminDAO.list();
    }

    public AdminEntity getByName(String username) {
        return adminDAO.getByUsername(username);
    }
}
