package com.cotton.doubee_admin.manager.impl;

import com.cotton.doubee_admin.manager.YouKuManager;
import com.cotton.doubee_admin.model.AdVideo;
import com.cotton.doubee_admin.model.Video;
import com.cotton.doubee_admin.service.AdVideoService;
import com.cotton.doubee_admin.service.VideoService;
import com.youku.uploader.YoukuUploader;
import org.apache.commons.io.FileUtils;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017-05-03.
 */

@Service
public class YouKuManagerImpl implements YouKuManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static YoukuUploader uploader;

    @Value("${youku.client_id}")
    private String client_id;

    @Value("${youku.client_secret}")
    private String client_secret;

    @Autowired
    private VideoService videoService;
    @Autowired
    private AdVideoService adVideoService;

    private static List<String> mineTypeList = new ArrayList<String>();

    static {
        mineTypeList.add("application/octet-stream");
        mineTypeList.add("application/pdf");
        mineTypeList.add("application/msword");

        mineTypeList.add("image/png");
        mineTypeList.add("image/jpg");
        mineTypeList.add("image/jpeg");
        mineTypeList.add("image/gif");
        mineTypeList.add("image/bmp");

        mineTypeList.add("audio/amr");
        mineTypeList.add("audio/mp3");
        mineTypeList.add("audio/aac");
        mineTypeList.add("audio/wma");
        mineTypeList.add("audio/wav");

        mineTypeList.add("video/mpeg");
        mineTypeList.add("video/mp4");
    }

    @Override
    public void uploadVideos() {

        //临时变量
        String access_token = "1958e1c42b72cb008a13f4e475ff1461";

        AdVideo model = new AdVideo();
        model.setStatus("normal");
        List<AdVideo> adVideoList = adVideoService.queryList(model);

        if(adVideoList != null && !adVideoList.isEmpty()) {

            for(AdVideo adVideo : adVideoList) {

                uploadVideo(access_token, adVideo);
            }
        }


    }

    /**
     *
     * @param access_token
     * @param adVideo
     */
    private void uploadVideo(String access_token, AdVideo adVideo) {

        //随机生成一个视频name
        String videoName = "videos/" +  UUID.randomUUID().toString().replaceAll("-","")+".mp4";


        //下载视频
        try {
            downLoadVideo(adVideo.getUrl(),videoName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //去掉前后的

        //上传youku
        String result = upload2Youku(access_token, videoName, adVideo.getTitle(), "宠物");

        //正确上传
        if(!StringUtils.isEmpty(result)){

            //更新数据库
            Video video = new Video();
            video.setStatus("normal");
            video.setTitle(adVideo.getTitle());
            video.setProviderId(adVideo.getMemberId());
            video.setLength(adVideo.getLength());
            video.setPosterUrl(adVideo.getPosterUrl());
            video.setUrl(result);
            video.setTags(adVideo.getTags());

            videoService.insert(video);

            //删除本地视频文件
            deleteVideo(videoName);

            //更新状态
            adVideo.setStatus("uploaded");
            adVideoService.update(adVideo);

        }else{

            logger.info("上传优酷失败：" + adVideo.getId());
        }
    }


    private String upload2Youku(String access_token, String videoName, String videoTitle, String videoTags) {

        //构建param
        HashMap<String, String> params, uploadInfo;
        params = new HashMap<String, String>();
        params.put("access_token", access_token);

        //构建uploadInfo
        uploadInfo = new HashMap<String, String>();
        uploadInfo.put("target", "cloud");
        uploadInfo.put("file_name", videoName);
        uploadInfo.put("title", videoTitle);
        uploadInfo.put("tags", videoTags);
        uploadInfo.put("public_type", "all");

        //上传
        uploader = new YoukuUploader(client_id, client_secret);
        return uploader.upload(params, uploadInfo, videoName, true);
    }

    private void deleteVideo(String filePath){
        try {
            FileUtils.forceDelete(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * HttpClient GET请求，可接受普通文本JSON等
     *
     * @param uri Y 请求URL，参数封装
     * @return 响应字符串
     * @author Jie
     * @date 2015-2-12
     */
    public  String downLoadVideo(String uri, String targetPath) throws IOException {
        logger.info("------------------------------HttpClient GET BEGIN-------------------------------");
        logger.info("GET:" + uri);
        if (org.apache.commons.lang3.StringUtils.isBlank(uri) || org.apache.commons.lang3.StringUtils.isBlank(targetPath)) {
            throw new RuntimeException(" uri or targetPath parameter is null or is empty!");
        }
        // 创建GET请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = null;
        String respContent = "";
        try {
            httpGet = new HttpGet(uri);
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();// 响应码
            String reasonPhrase = statusLine.getReasonPhrase();// 响应信息
            if (statusCode == 200) {// 请求成功
                // 获取响应MineType
                mineTypeList.add("video/mp4");
                HttpEntity entity = response.getEntity();
                ContentType contentType = ContentType.get(entity);
                if (mineTypeList.contains(contentType.getMimeType().toLowerCase())) {
                    logger.info("MineType:" + contentType.getMimeType());

                    if (targetPath.contains(".")) {
                        targetPath = targetPath.substring(0, targetPath.lastIndexOf(".")) + "."
                                + contentType.getMimeType().split("/")[1];
                    } else if (targetPath.endsWith(File.separator)) {
                        targetPath += UUID.randomUUID().toString() + "." + contentType.getMimeType().split("/")[1];
                    } else {
                        targetPath += File.separator + UUID.randomUUID().toString() + "."
                                + contentType.getMimeType().split("/")[1];
                    }
                    // 写入磁盘
                    respContent = writeFile(entity.getContent(), targetPath);
                } else {
                    respContent = repsonse(response);
                }
            } else {
                logger.error("resp：code[" + statusCode + "],desc[" + reasonPhrase + "]");
                throw new RuntimeException("请求失败，请检查请求地址及参数");
            }
        } finally {
            if (httpGet != null)
                httpGet.releaseConnection();
            if (httpClient != null)
                // noinspection ThrowFromFinallyBlock
                httpClient.close();
        }
        logger.info("resp：" + respContent);
        logger.info("------------------------------HttpClient GET END-------------------------------");
        return respContent;
    }

    /**
     * 获文件类型
     * @param file 目标文件
     * @return MimeType
     * @author Jie
     * @date 2015-2-28
     */
    public static String getMimeType(File file) {
        return new MimetypesFileTypeMap().getContentType(file);
    }

    /**
     * 写入文件到目标磁盘中
     *
     * @param in 文件输入流
     * @param targetPath 文件存放目标绝对路径(包含文件)
     * @return
     * @author Jie
     * @throws Exception
     * @date 2015-2-12
     */
    public  String writeFile(InputStream in, String targetPath) throws IOException {
        if (in == null) {
            logger.error("The InputStream is null");
            return "未能获取到输入流";
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(targetPath)) {
            logger.error("The targetPath is null");
            return "文件保存路径不可为空";
        }
        OutputStream os = null;
        try {
            File file = new File(targetPath);
            if (file.isDirectory()) {
                return "保存的文件应是一个文件，而非一个目录";
            }
            os = new FileOutputStream(file);
            int len = 0;
            byte[] ch = new byte[1024];
            while ((len = in.read(ch)) != -1) {
                os.write(ch, 0, len);
            }
            logger.info("File save success : " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return "保存文件到磁盘异常：" + e.getMessage();
        } finally {
            close(null,os, null,null,null);
        }
        return "成功";
    }

    /**
     * 获取响应内容，针对MimeType为text/plan、text/json格式
     *
     * @param response HttpResponse对象
     * @return 转为UTF-8的字符串
     * @author Jie
     * @date 2015-2-28
     */
    private  String repsonse(HttpResponse response) throws ParseException, IOException {
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();// 响应码
        String reasonPhrase = statusLine.getReasonPhrase();// 响应信息
        StringBuilder content = new StringBuilder();
        if (statusCode == HttpStatus.SC_OK) {// 请求成功
            HttpEntity entity = response.getEntity();
            ContentType contentType = ContentType.get(entity);
            logger.info("MineType：" + contentType.getMimeType());
            content.append(EntityUtils.toString(entity, Consts.UTF_8));
        } else {
            content.append("code[").append(statusCode).append("],desc[").append(reasonPhrase).append("]");
        }
        return content.toString().replace("\r\n", "").replace("\n", "");
    }

    // 释放资源
    private void close(File tempFile, OutputStream os, InputStream is, HttpPost httpPost,
                       CloseableHttpClient httpClient) throws IOException {
        if (tempFile != null && tempFile.exists() && !tempFile.delete()) {
            tempFile.deleteOnExit();
        }
        if (os != null) {
            os.close();
        }
        if (is != null) {
            is.close();
        }
        if (httpPost != null) {
            // 释放资源
            httpPost.releaseConnection();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }
}

