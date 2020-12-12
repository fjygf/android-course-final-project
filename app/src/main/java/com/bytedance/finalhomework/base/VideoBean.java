package com.bytedance.finalhomework.base;

public class VideoBean {
    private int videoId;
    /** 视频播放资源 */
    private int videoRes;
    /** 封面图片资源 */
    private String coverRes;
    /** 视频url */
    private String videoUrl;
    /**大作业的附加信息*/
    private String content;
    /** 作者 */
    private UserBean userBean;


    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getVideoRes() {
        return videoRes;
    }

    public void setVideoRes(int videoRes) {
        this.videoRes = videoRes;
    }

    public String getCoverRes() {
        return coverRes;
    }

    public void setCoverRes(String coverRes) {
        this.coverRes = coverRes;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }


    public static class UserBean {
        private String uid;
        private String nickName;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickName() {
            return nickName == null ? "" : nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }
}

