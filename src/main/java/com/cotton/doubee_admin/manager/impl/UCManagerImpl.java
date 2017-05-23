package com.cotton.doubee_admin.manager.impl;

import com.cotton.base.enumeration.Status;
import com.cotton.base.util.HttpUtil;
import com.cotton.doubee_admin.model.Member;
import com.cotton.doubee_admin.service.MemberService;
import com.cotton.doubee_admin.manager.UCManager;
import com.cotton.doubee_admin.model.AdVideo;
import com.cotton.doubee_admin.model.UCResult;
import com.cotton.doubee_admin.service.AdVideoService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
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

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017-05-03.
 */

@Service
public class UCManagerImpl implements UCManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${uc.url}")
    private String uc_url;

    @Value("${uc.video_url}")
    private String video_url;

    @Autowired
    private AdVideoService adVideoService;

    @Autowired
    private MemberService memberService;

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
    public void getUCVideos() {


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("method", "new");
        params.put("tab", "video");
        params.put("dn", 0);
        String res = HttpUtil.doGet(uc_url, params);

        logger.info(res);

        UCResult ucResult = new Gson().fromJson(res, UCResult.class);

        if (ucResult != null && ucResult.getData() != null &&
                ucResult.getData().getArticles() != null && !ucResult.getData().getArticles().isEmpty()) {

            for (UCResult.UCArticle value : ucResult.getData().getArticles().values()) {


                if (value.getVideos() != null && !value.getVideos().isEmpty()) {
                    String videosId = value.getVideos().get(0).getVideo_id();

                    //查找改视频是否已经存在

                    AdVideo model = new AdVideo();
                    model.setOutId(videosId);

                    List<AdVideo> adVideoList = adVideoService.queryList(model);

                    if (adVideoList.isEmpty()) {

                        //解析视频真实地址
                        String urlSign = getUrlSign(value.getZzd_url());

                        Map<String, Object> params2 = new HashMap<String, Object>();
                        params2.put("app", "uc-iflow");
                        params2.put("aid", videosId);
                        params2.put("pageUrl", value.getVideos().get(0).getUrl());
                        params2.put("url_sign", urlSign);
                        String res2 = HttpUtil.doGet(video_url, params2);
                        UCResult.VideoResult videoResult = new Gson().fromJson(res2, UCResult.VideoResult.class);

                        if(videoResult != null && videoResult.getData() != null) {


                            //插入作者相关
                            UCResult.UCAuthor ucAuthor = value.getSite_logo();

                            //视频有作者信息 并且该作者没有录入过数据库
                            Member member = memberService.getByProviderId(ucAuthor.getId());
                            if (ucAuthor != null && member == null) {

                                member = new Member();
                                member.setbProvider(1);
                                member.setProviderId(Long.valueOf(ucAuthor.getId()));
                                member.setName(ucAuthor.getDesc());
                                if (ucAuthor.getImg() != null) {
                                    member.setHeadPortrait(ucAuthor.getImg().getUrl());
                                }
                                member.setStatus(Status.normal.toString());
                                member.setCreatedAt(new Date());
                                memberService.insert(member);

                            }

                            //插入视频

                            AdVideo adVideo = new AdVideo();
                            adVideo.setMemberId(member.getId());
                            adVideo.setOutId(videosId);
                            adVideo.setTitle(value.getTitle());
                            adVideo.setSubhead(value.getSubhead());
                            adVideo.setLength(Long.valueOf(value.getVideos().get(0).getLength()));
                            adVideo.setPosterUrl(value.getVideos().get(0).getPoster().getUrl());
                            adVideo.setUrl(videoResult.getData().getVideoList().get(0).getFragment().get(0).getUrl());
                            adVideo.setTags(value.getTags().toString());
                            adVideoService.insert(adVideo);
                        }
                    }
                }
            }
        }
    }

    private String getUrlSign(String url) {
        String s = getHtmlContent(url);
        s = getScript(s);
        s = getSing(s);
        s = getSign(s);
        return s;
    }

    private String getHtmlContent(String htmlurl) {
        URL url;
        String temp;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(htmlurl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));// 读取网页全部内容
            while ((temp = in.readLine()) != null) {
                sb.append(temp);
            }
            in.close();
        } catch (final MalformedURLException me) {
            System.out.println("你输入的URL格式有问题!");
            me.getMessage();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getScript(String s) {
        String regex;

        regex = "<script.*?</script>";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(s);
        while (ma.find()) {

            if (ma.group().contains("var xissJsonData")) {
                return ma.group();
            }
        }

        return null;

    }

    public String getSing(String s) {
        String regex;

        regex = "\"url_sign\":\".*?\"";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(s);
        while (ma.find()) {

            return ma.group();
        }
        return null;
    }

    private String getSign(String s) {

        return s.substring(12, s.length() - 1);

    }

    @Override
    public void test() {
        try {
            getForDownloadStream("http://video.ums.uc.cn/20170502/2c10109b8393405e/video_ums_33903510-100839996-2.mp4?auth_key=1495524682-0-0-f1e38e6e06dcfc14016dcea40534bd4d","1xx1.mp4");
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
    public  String getForDownloadStream(String uri, String targetPath) throws IOException {
        logger.info("------------------------------HttpClient GET BEGIN-------------------------------");
        logger.info("GET:" + uri);
        if (StringUtils.isBlank(uri) || StringUtils.isBlank(targetPath)) {
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
        if (StringUtils.isBlank(targetPath)) {
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
