package com.cotton.doubee_admin.web.timer;

import com.cotton.doubee_admin.manager.UCManager;
import com.cotton.doubee_admin.manager.YouKuManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 视频数据源定时器
 */
@Component
public class VideoSourceScheduler {

    @Autowired
    private UCManager ucManager;
    @Autowired
    private YouKuManager youKuManager;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 读取uc宠物视频 - 每十分钟触发一次
     */
    //@Scheduled(cron = "0 0/5 * * * ?")
    public void getUCVideosScheduler() {

        logger.info("获取uc视频开始……");

        ucManager.getUCVideos();

        logger.info("获取uc视频结束。");
    }


    /**
     * 上传优酷 - 每十分钟触发一次
     */
    //@Scheduled(cron = "0 0/10 * * * ?")
    public void uploadYouKuScheduler() {

        logger.info("上传优酷开始……");

        youKuManager.uploadVideos();

        logger.info("上传优酷结束。");
    }
}