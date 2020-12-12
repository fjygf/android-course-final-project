package com.bytedance.finalhomework.activity;
/*
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bytedance.finalhomework.R;
import com.bytedance.finalhomework.base.BaseActivity;
import com.bytedance.finalhomework.fragment.RecommendFragment;

public class PlayListActivity extends BaseActivity {
    public static int initPos;//具体播放哪个视频

    @Override
    protected int setLayoutId() {
        return R.layout.activity_play_list;
    }

    @Override
    protected void init() {
        //实际上调用的还是RecommendFragment,目前先调用一个空的
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, new RecommendFragment()).commit();
    }
}
*/
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.finalhomework.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.bytedance.finalhomework.data.VideoInfo;
import com.bytedance.finalhomework.view.MyVideoView;

public class PlayListActivity extends AppCompatActivity {

    @BindView(R.id.video_view)
    MyVideoView mVideoView;

    @BindView(R.id.video_thumb)
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list_1);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        String videourl = "null";
        videourl = bundle.getString("videourl");
        Log.i("获取到的videourl值为", videourl);
        String imageurl = "null";
        imageurl = bundle.getString("imageurl");
        Log.i("获取到的imageurl值为", imageurl);

        //imageurl, videourl的默认值
        String mVideoUrl = "https://sf3-hscdn-tos.pstatp.com/obj/developer-baas/baas/tttyvl/c0fa180944b514a5_1607568068884.mp4";
        String mImageUrl = "https://sf1-hscdn-tos.pstatp.com/obj/developer-baas/baas/tttyvl/773fc072c5760dca_1607568068597.png";

        long time = 2;
        //获得网络视频的时间给进度条提供参考
        if(!videourl.equals("null")) {
            time = getPlayTime(videourl);
        } else {
            time = getPlayTime(mVideoUrl);
        }

        //使主线程可以网络请求
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ButterKnife.bind(this);

        //视频
        if(!videourl.equals("null")) {
            VideoInfo videoInfo = new VideoInfo(
                    videourl,
                    time,
                    640,
                    800
            );
            mVideoView.setVideo(videoInfo);
        } else {
            VideoInfo videoInfo = new VideoInfo(
                    mVideoUrl,
                    time,
                    640,
                    800
            );
            mVideoView.setVideo(videoInfo);
        }
        mVideoView.setProgressBarVisible(true);

        //播放视频前的封面图
        if(!imageurl.equals("null")) {
            try {
                Bitmap bitmap = getBitmap(imageurl);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Bitmap bitmap = getBitmap(mImageUrl);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getBitmap(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.resume();
    }

    private long getPlayTime(String mUri)
    {
        long res = 0;
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
        try {
            if (mUri != null)
            {
                HashMap<String, String> headers = null;
                if (headers == null)
                {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(mUri, headers);
            } else
            {

            }

            String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            String width = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//宽
            String height = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//高

            res =Integer.parseInt(duration);

            Toast.makeText(PlayListActivity.this, "playtime:"+ duration+"w="+width+"h="+height, Toast.LENGTH_SHORT).show();

        } catch (Exception ex)
        {
            Log.e("TAG", "MediaMetadataRetriever exception " + ex);
        } finally {
            mmr.release();
        }
        return res / 1000 + 1;
    }
}