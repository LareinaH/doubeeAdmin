package com.cotton.doubee_admin.manager.impl;

import com.cotton.doubee_admin.manager.YouKuManager;
import com.youku.uploader.YoukuUploader;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by Administrator on 2017-05-03.
 */

@Service
public class YouKuManagerImpl implements YouKuManager {

    private static YoukuUploader uploader;

    @Override
    public void uploadVideos() {

        String client_id = "d5f4c9547b1f964e";
        String client_secret = "1bbe8f29f4bacafac5107bdd2923aec6";
        String result = "";
        String access_token = "1958e1c42b72cb008a13f4e475ff1461";

        HashMap<String, String> params, uploadInfo;
        String filename = "1xx1.mp4";//视频绝对路径
        params = new HashMap<String, String>();
        params.put("access_token", access_token);
        uploadInfo = new HashMap<String, String>();
        uploadInfo.put("target", "cloud"); // 上传目标可以选择上传至优酷或者视频云 必填 youku or cloud
        uploadInfo.put("file_name", filename); // 指定：文件名
        uploadInfo.put("title", "那只被咬舌头的柴柴,它又作死了"); // 指定：标题
        uploadInfo.put("tags", "宠物"); // 指定：分类
        uploadInfo.put("public_type", "all"); //视频公开类型（all：公开（默认），friend：仅好友，password：需要输入密码才能观看）
        //  uploadInfo.put("watch_password", "123456"); //密码，当public_type为password时，必填
        uploader = new YoukuUploader(client_id, client_secret);
        result = uploader.upload(params, uploadInfo, filename, true); // 第四个参数：boolean类型（true：显示进度 false：不显示进度）
        System.out.print(result); //返回视频id

    }
}

