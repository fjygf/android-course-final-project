package com.bytedance.finalhomework.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetVideoResponse {
    @SerializedName("feeds")
    List<VideoModel> feeds;

    @SerializedName("success")
    boolean success;

    public List<VideoModel> getFeeds() {
        return feeds;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "GetVideoResponse{" +
                "success=" + success +
                ", feeds=" + feeds +
                ", success=" + success +
                '}';
    }
}