package com.bytedance.finalhomework.base;

import android.util.Log;
import android.widget.TextView;
import android.widget.VideoView;

import com.bytedance.finalhomework.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
* 从接口出接收数据*/
public class DataCreate {
    final String TAG = "777777777777";
    public static ArrayList<VideoBean> datas = new ArrayList<>();
    public static ArrayList<VideoBean.UserBean> userList = new ArrayList<>();
    //引入retrofit导入网上的视频
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api-sjtu-camp.bytedance.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private GitHubService testService = retrofit.create(GitHubService.class);


    public void initData() {

        Call<GetVideoResponse> videoResponseCall = testService.getVideoList(TAG);//只下载我上传的视频
//        Call<GetVideoResponse> videoResponseCall = testService.getVideoList();//拉取所有人的视频
        videoResponseCall.enqueue(new Callback<GetVideoResponse>() {

            @Override
            public void onResponse(Call<GetVideoResponse> call, Response<GetVideoResponse> response) {
                Log.d(TAG, "onResponse: received");
//                Log.d(TAG, "onResponse: " + response.body());
                if (!response.isSuccessful()) {
                    return;
                }
                if(response.isSuccessful()){
                    Log.d(TAG,"读取成功");
                }
                final List<VideoModel> list = response.body().getFeeds();
                if (list == null || list.isEmpty()) {
                    Log.d(TAG,"空列表");
                    return;
                }
                for (int i = 0; i < 10 || i < list.size(); i++) {
                    final VideoModel videoModel = list.get(i);

                    VideoBean videoBeanOne = new VideoBean();
                    videoBeanOne.setCoverRes(videoModel.getImageUrl());//这里传封面
                    videoBeanOne.setVideoUrl(videoModel.getVideoUrl());//这里传视频
                    String s = videoModel.getExtraValue();
                    if(s == null || s.length() == 0){
                        s = "天气真好";
                    }
                    videoBeanOne.setContent(s);//这里传其他信息
                    videoBeanOne.setVideoRes(R.raw.video1);//视频信息
                    VideoBean.UserBean userBeanOne = new VideoBean.UserBean();
                    userBeanOne.setUid(videoModel.getStudentId());
                    userBeanOne.setNickName(videoModel.getUserName());
                    userList.add(userBeanOne);
                    videoBeanOne.setUserBean(userBeanOne);
                    datas.add(videoBeanOne);
                }
            }
            @Override
            public void onFailure(Call<GetVideoResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
                t.printStackTrace();
            }
        });
    }
}
