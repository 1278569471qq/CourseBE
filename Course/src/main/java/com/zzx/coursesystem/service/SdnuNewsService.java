package com.zzx.coursesystem.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zzx.coursesystem.manager.SdnuNewsManager;
import com.zzx.coursesystem.model.bo.SdnuNewsBO;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.model.vo.response.table.SdnuNewsItemVO;

@Service
public class SdnuNewsService extends BaseService {
    private final SdnuNewsManager manager;

    public SdnuNewsService(SdnuNewsManager manager) {
        this.manager = manager;
    }

    public ResultVO getAllNews() {
        List<SdnuNewsItemVO> voList = SdnuNewsItemVO.fromSdnuNewsBOList(manager.getAllNews());
        voList.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        return result(voList);
    }

    public ResultVO getLatestAllNews() {
        List<SdnuNewsBO> voList = manager.getLatestAllNews();
        voList.sort(Comparator.comparingInt(v -> Integer.parseInt(v.getDate())));

        return result(voList);
    }
}
