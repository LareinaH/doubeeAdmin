package com.cotton.doubee_admin.web.controller;

import com.cotton.doubee_admin.manager.UCManager;
import com.cotton.doubee_admin.manager.YouKuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017-05-20.
 */
@Controller
public class Test {

    @Autowired
    private UCManager ucManager;
    @Autowired
    private YouKuManager youKuManager;

    @ResponseBody
    @RequestMapping(name = "/test")
    public void getUCVideosScheduler() {

        //ucManager.getUCVideos();

        //ucManager.test();

        youKuManager.uploadVideos();

    }
}


