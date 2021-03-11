package com.zzx.coursesystem.controller;

import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.service.SdnuNewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sdnu/news")
@RestController
public class SdnuNewsController extends BaseController {
    private final SdnuNewsService service;

    public SdnuNewsController(SdnuNewsService service) {
        this.service = service;
    }

    @GetMapping
    public ResultVO get() {
        return service.getAllNews();
    }


    @GetMapping("/new")
    public ResultVO getLatest() {
        return service.getLatestAllNews();
    }
}
