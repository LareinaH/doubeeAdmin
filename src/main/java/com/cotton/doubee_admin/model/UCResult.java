package com.cotton.doubee_admin.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-05-04.
 */
public class UCResult {

    private  UCData data;

    private int status;
    private String message;
    private ResultBean result;

    public UCData getData() {
        return data;
    }

    public void setData(UCData data) {
        this.data = data;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }


    public static class ResultBean {
        /**
         * status : 0
         * message : ok
         */

        private int status;
        private String message;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public class UCData {

        private   Map<String,UCArticle> articles;


        public Map<String, UCArticle> getArticles() {
            return articles;
        }

        public void setArticles(Map<String, UCArticle> articles) {
            this.articles = articles;
        }

    }


    public class UCArticle {

        private String id;

        private String title;

        private String subhead;

        private List<String> tags;

        private List<UCVideo> videos;

        private UCAuthor site_logo;

        private String zzd_url;

        public String getZzd_url() {
            return zzd_url;
        }

        public void setZzd_url(String zzd_url) {
            this.zzd_url = zzd_url;
        }

        public UCAuthor getSite_logo() {
            return site_logo;
        }

        public void setSite_logo(UCAuthor site_logo) {
            this.site_logo = site_logo;
        }

        public List<UCVideo> getVideos() {
            return videos;
        }

        public void setVideos(List<UCVideo> videos) {
            this.videos = videos;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getSubhead() {
            return subhead;
        }

        public void setSubhead(String subhead) {
            this.subhead = subhead;
        }
    }

    public class UCAuthor{


        private int id;
        private int style;
        private String desc;
        private String link;
        private ImgBean img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public ImgBean getImg() {
            return img;
        }

        public void setImg(ImgBean img) {
            this.img = img;
        }

        public  class ImgBean {
            /**
             * url : http://image.uczzd.cn/15993568175410203308.jpg?id=0
             * width : 75
             * height : 75
             * type :
             * preload : 0
             * daoliu_url :
             * daoliu_title :
             */

            private String url;
            private int width;
            private int height;
            private String type;
            private int preload;
            private String daoliu_url;
            private String daoliu_title;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getPreload() {
                return preload;
            }

            public void setPreload(int preload) {
                this.preload = preload;
            }

            public String getDaoliu_url() {
                return daoliu_url;
            }

            public void setDaoliu_url(String daoliu_url) {
                this.daoliu_url = daoliu_url;
            }

            public String getDaoliu_title() {
                return daoliu_title;
            }

            public void setDaoliu_title(String daoliu_title) {
                this.daoliu_title = daoliu_title;
            }
        }
    }

    public class UCVideo{

        private String url;
        private int length;
        private PosterBean poster;
        private boolean assembled;
        private boolean channel_play;
        private String video_id;


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }


        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public PosterBean getPoster() {
            return poster;
        }

        public void setPoster(PosterBean poster) {
            this.poster = poster;
        }

        public boolean isAssembled() {
            return assembled;
        }

        public void setAssembled(boolean assembled) {
            this.assembled = assembled;
        }


        public boolean isChannel_play() {
            return channel_play;
        }

        public void setChannel_play(boolean channel_play) {
            this.channel_play = channel_play;
        }


        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }


        public  class PosterBean {
            /**
             * url : http://image.uczzd.cn/1698683897042629112.jpg?id=0
             * width : 463
             * height : 350
             * type : jpg
             * preload : 1
             * daoliu_url :
             * daoliu_title :
             */

            private String url;
            private int width;
            private int height;
            private String type;
            private int preload;
            private String daoliu_url;
            private String daoliu_title;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getPreload() {
                return preload;
            }

            public void setPreload(int preload) {
                this.preload = preload;
            }

            public String getDaoliu_url() {
                return daoliu_url;
            }

            public void setDaoliu_url(String daoliu_url) {
                this.daoliu_url = daoliu_url;
            }

            public String getDaoliu_title() {
                return daoliu_title;
            }

            public void setDaoliu_title(String daoliu_title) {
                this.daoliu_title = daoliu_title;
            }
        }

    }

    public class VideoResult{

        /**
         * data : {"posterList":[{"width":0,"url":"http://img.ums.uc.cn/snapshot/9832ce4c-39e8-11e7-963c-cc53b5fb4581@375w_220h_100q.jpg","height":0}],"code":0,"videoList":[{"duration":16,"headers":[],"fragment":[{"duration":16,"url":"http://video.ums.uc.cn/video/wemedia/5ff7b8e4c7de4a8aa4d833887a6ec03b/3fd3a48e2c879a2e368ad905a0f94746-105598807-1.mp4?auth_key=1495272900-0-0-be566d86a64b3d8df6b8467e190fab37"}],"format":"mp4","resolution":"normal"}],"seasonList":[],"pageUrl":"http://v.ums.uc.cn/video/v_2c8bfdfc5049721e.html","source":"ums.uc.cn","resolutionList":["normal"],"title":"http://v.ums.uc.cn/video/v_2c8bfdfc5049721e.html","langList":[],"errorMsg":null}
         * status : 0
         * message : ok
         * result : {"status":0,"message":"ok"}
         */

        private DataBean data;
        private int status;
        private String message;
        private ResultBean result;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public  class DataBean {
            /**
             * posterList : [{"width":0,"url":"http://img.ums.uc.cn/snapshot/9832ce4c-39e8-11e7-963c-cc53b5fb4581@375w_220h_100q.jpg","height":0}]
             * code : 0
             * videoList : [{"duration":16,"headers":[],"fragment":[{"duration":16,"url":"http://video.ums.uc.cn/video/wemedia/5ff7b8e4c7de4a8aa4d833887a6ec03b/3fd3a48e2c879a2e368ad905a0f94746-105598807-1.mp4?auth_key=1495272900-0-0-be566d86a64b3d8df6b8467e190fab37"}],"format":"mp4","resolution":"normal"}]
             * seasonList : []
             * pageUrl : http://v.ums.uc.cn/video/v_2c8bfdfc5049721e.html
             * source : ums.uc.cn
             * resolutionList : ["normal"]
             * title : http://v.ums.uc.cn/video/v_2c8bfdfc5049721e.html
             * langList : []
             * errorMsg : null
             */

            private int code;
            private String pageUrl;
            private String source;
            private String title;
            private Object errorMsg;
            private List<PosterListBean> posterList;
            private List<VideoListBean> videoList;
            private List<?> seasonList;
            private List<String> resolutionList;
            private List<?> langList;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getPageUrl() {
                return pageUrl;
            }

            public void setPageUrl(String pageUrl) {
                this.pageUrl = pageUrl;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getErrorMsg() {
                return errorMsg;
            }

            public void setErrorMsg(Object errorMsg) {
                this.errorMsg = errorMsg;
            }

            public List<PosterListBean> getPosterList() {
                return posterList;
            }

            public void setPosterList(List<PosterListBean> posterList) {
                this.posterList = posterList;
            }

            public List<VideoListBean> getVideoList() {
                return videoList;
            }

            public void setVideoList(List<VideoListBean> videoList) {
                this.videoList = videoList;
            }

            public List<?> getSeasonList() {
                return seasonList;
            }

            public void setSeasonList(List<?> seasonList) {
                this.seasonList = seasonList;
            }

            public List<String> getResolutionList() {
                return resolutionList;
            }

            public void setResolutionList(List<String> resolutionList) {
                this.resolutionList = resolutionList;
            }

            public List<?> getLangList() {
                return langList;
            }

            public void setLangList(List<?> langList) {
                this.langList = langList;
            }

            public  class PosterListBean {
                /**
                 * width : 0
                 * url : http://img.ums.uc.cn/snapshot/9832ce4c-39e8-11e7-963c-cc53b5fb4581@375w_220h_100q.jpg
                 * height : 0
                 */

                private int width;
                private String url;
                private int height;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }
            }

            public  class VideoListBean {
                /**
                 * duration : 16
                 * headers : []
                 * fragment : [{"duration":16,"url":"http://video.ums.uc.cn/video/wemedia/5ff7b8e4c7de4a8aa4d833887a6ec03b/3fd3a48e2c879a2e368ad905a0f94746-105598807-1.mp4?auth_key=1495272900-0-0-be566d86a64b3d8df6b8467e190fab37"}]
                 * format : mp4
                 * resolution : normal
                 */

                private int duration;
                private String format;
                private String resolution;
                private List<?> headers;
                private List<FragmentBean> fragment;

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public String getFormat() {
                    return format;
                }

                public void setFormat(String format) {
                    this.format = format;
                }

                public String getResolution() {
                    return resolution;
                }

                public void setResolution(String resolution) {
                    this.resolution = resolution;
                }

                public List<?> getHeaders() {
                    return headers;
                }

                public void setHeaders(List<?> headers) {
                    this.headers = headers;
                }

                public List<FragmentBean> getFragment() {
                    return fragment;
                }

                public void setFragment(List<FragmentBean> fragment) {
                    this.fragment = fragment;
                }

                public  class FragmentBean {
                    /**
                     * duration : 16
                     * url : http://video.ums.uc.cn/video/wemedia/5ff7b8e4c7de4a8aa4d833887a6ec03b/3fd3a48e2c879a2e368ad905a0f94746-105598807-1.mp4?auth_key=1495272900-0-0-be566d86a64b3d8df6b8467e190fab37
                     */

                    private int duration;
                    private String url;

                    public int getDuration() {
                        return duration;
                    }

                    public void setDuration(int duration) {
                        this.duration = duration;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }
        }

        public  class ResultBean {
            /**
             * status : 0
             * message : ok
             */

            private int status;
            private String message;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }
    }
}
