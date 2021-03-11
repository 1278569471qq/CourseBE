package com.zzx.coursesystem.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.zzx.coursesystem.manager.LoginStatusManager;
import com.zzx.coursesystem.model.bo.LoginStatusBO;
import com.zzx.coursesystem.model.vo.response.IdNameVO;
import com.zzx.coursesystem.model.vo.response.ResultVO;

public class BaseService {
    @Autowired
    private HttpSession session;
    @Autowired
    private LoginStatusManager loginStatusManager;

    private LoginStatusBO getLoginStatus() {
        return loginStatusManager.getLoginStatus(session);
    }

    protected Integer getUserId() {
        return getLoginStatus().getUserId();
    }

    protected ResultVO result(Object data) {
        return new ResultVO(ResultVO.SUCCESS, "success", data);
    }

    protected ResultVO result(Object data, String message) {
        return new ResultVO(ResultVO.SUCCESS, message, data);
    }

    protected ResultVO failedResult(String message) {
        return new ResultVO(ResultVO.FAIL, message, null);
    }

    protected ResultVO failedResult(String message, Object data) {
        return new ResultVO(ResultVO.FAIL, message, data);
    }

    protected  boolean repeat(List<IdNameVO> idNameVOS,
                              String name) {
        List<IdNameVO> collect =
                idNameVOS.stream().filter(vo -> vo.getName()
                        .equals(name)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            return true;
        }
        return false;
    }
}
