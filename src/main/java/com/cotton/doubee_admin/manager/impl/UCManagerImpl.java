package com.cotton.doubee_admin.manager.impl;

import com.cotton.base.enumeration.Status;
import com.cotton.base.util.HttpUtil;
import com.cotton.doubee_admin.manager.UCManager;
import com.cotton.doubee_admin.model.AdVideo;
import com.cotton.doubee_admin.model.Member;
import com.cotton.doubee_admin.model.UCResult;
import com.cotton.doubee_admin.service.AdVideoService;
import com.cotton.doubee_admin.service.MemberService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                            adVideo.setStatus("normal");
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

    private String getHtmlContent(String htmlUrl) {
        URL url;
        String temp;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(htmlUrl);
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

    private String getScript(String s) {
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

    private String getSing(String s) {
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


}
