package com.cotton.doubee_admin.model;

import com.cotton.base.model.BaseModel;
import java.util.Date;
import javax.persistence.*;

@Table(name = "video")
public class Video extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 提供者id
     */
    private Long providerId;

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subhead;

    /**
     * 视频长度
     */
    private Long length;

    /**
     * 首图地址
     */
    private String posterUrl;

    /**
     * 视频地址
     */
    private String url;

    private String tags;

    private Long likeCount;

    private Long dislikeCount;

    private Long browseCount;

    /**
     * 状态【normal-正常，delete-删除】
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取提供者id
     *
     * @return providerId - 提供者id
     */
    public Long getProviderId() {
        return providerId;
    }

    /**
     * 设置提供者id
     *
     * @param providerId 提供者id
     */
    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取副标题
     *
     * @return subhead - 副标题
     */
    public String getSubhead() {
        return subhead;
    }

    /**
     * 设置副标题
     *
     * @param subhead 副标题
     */
    public void setSubhead(String subhead) {
        this.subhead = subhead == null ? null : subhead.trim();
    }

    /**
     * 获取视频长度
     *
     * @return length - 视频长度
     */
    public Long getLength() {
        return length;
    }

    /**
     * 设置视频长度
     *
     * @param length 视频长度
     */
    public void setLength(Long length) {
        this.length = length;
    }

    /**
     * 获取首图地址
     *
     * @return posterUrl - 首图地址
     */
    public String getPosterUrl() {
        return posterUrl;
    }

    /**
     * 设置首图地址
     *
     * @param posterUrl 首图地址
     */
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl == null ? null : posterUrl.trim();
    }

    /**
     * 获取视频地址
     *
     * @return url - 视频地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置视频地址
     *
     * @param url 视频地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return tags
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags
     */
    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    /**
     * @return likeCount
     */
    public Long getLikeCount() {
        return likeCount;
    }

    /**
     * @param likeCount
     */
    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * @return dislikeCount
     */
    public Long getDislikeCount() {
        return dislikeCount;
    }

    /**
     * @param dislikeCount
     */
    public void setDislikeCount(Long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    /**
     * @return browseCount
     */
    public Long getBrowseCount() {
        return browseCount;
    }

    /**
     * @param browseCount
     */
    public void setBrowseCount(Long browseCount) {
        this.browseCount = browseCount;
    }

    /**
     * 获取状态【normal-正常，delete-删除】
     *
     * @return status - 状态【normal-正常，delete-删除】
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态【normal-正常，delete-删除】
     *
     * @param status 状态【normal-正常，delete-删除】
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取创建时间
     *
     * @return createdAt - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取更新时间
     *
     * @return updateAt - 更新时间
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置更新时间
     *
     * @param updateAt 更新时间
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}