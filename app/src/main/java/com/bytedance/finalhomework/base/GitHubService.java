package com.bytedance.finalhomework.base;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface GitHubService {

    /**
     * 假设我们现在传入的 userName 参数为 JakeWharton，那么经过在程序运行时，经过 @Path 的匹配
     * 替换掉 {username} 之后就会变成 users/JakeWharton/repos
     */

    @Multipart
    @POST("/invoke/video")
//实现上传功能
    Call<ResponseBody> post(@Query("student_id") String studentId, @Query("user_name") String userName,
                            @Query("extra_value") String extraValue,
                            @Part MultipartBody.Part cover_image, @Part MultipartBody.Part video);
//下载功能
    @GET("/invoke/video")
    Call<GetVideoResponse> getVideoList(@Query("student_id") String studentId);//不传就拉取所有人的视频
    @GET("/invoke/video")
    Call<GetVideoResponse> getVideoList();

}

