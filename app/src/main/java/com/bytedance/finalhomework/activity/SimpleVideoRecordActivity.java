package com.bytedance.finalhomework.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bytedance.finalhomework.R;
import com.bytedance.finalhomework.base.GitHubService;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.os.Environment.DIRECTORY_DCIM;

public class SimpleVideoRecordActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api-sjtu-camp.bytedance.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private GitHubService testService = retrofit.create(GitHubService.class);
    private Button mRecordButton;
    private VideoView mVideoView;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    static final int REQUEST_CAMERA_PERMISSION = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_video_record);
        mRecordButton = findViewById(R.id.record);
        mVideoView = findViewById(R.id.video_view);

        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(SimpleVideoRecordActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED|| ContextCompat.checkSelfPermission(SimpleVideoRecordActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(SimpleVideoRecordActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SimpleVideoRecordActivity.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.RECORD_AUDIO}, REQUEST_CAMERA_PERMISSION);
                } else {
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                }
            }
        });
    }

    //调用权限
    private boolean checkPermissionAndStartRecord() {
        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

            Uri videoURI =  data.getData();
            mVideoView.setVideoURI(videoURI);

            String mCurrentPhotoPath = getRealPathFromURI(videoURI);
//            mVideoView.setVideoPath(mCurrentPhotoPath);
            mVideoView.start();
            upLoad(mCurrentPhotoPath);
            Log.i("77777777",videoURI.toString());
            Log.i("77777777", mCurrentPhotoPath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);

        }
        cursor.close();
        return res;
    }
    //图片转二进制
    private MultipartBody.Part getMultipartFroAssetImage(){
        final String partKey = "cover_image";
        final String localFileName = "pic.jpg";
        final AssetManager assetManager = getAssets();
        RequestBody requestFile;
        try {
            final InputStream inputStream = assetManager.open(localFileName);
            requestFile = RequestBody
                    .create(MediaType.parse("multipart/form-data"),toByteArray(inputStream));
            return MultipartBody.Part.createFormData(partKey, localFileName, requestFile);
        }catch (IOException e){
            e.printStackTrace();
            return null;//!!!不确定
        }

    }
    private void upLoad(String mCurrentPhotoPath) {

        testService.post("777777777777","yf","our sjtu123",getMultipartFroAssetImage(),getMultipartFroAssetVideo(mCurrentPhotoPath)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    Log.d("77777777","写入成功");

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    //视频转二进制
    private MultipartBody.Part getMultipartFroAssetVideo(String mCurrentPhotoPath) {
        RequestBody requestFile;
        try {
            File file= new File(mCurrentPhotoPath);
            FileInputStream fis = new FileInputStream(file);
            requestFile = RequestBody
                    .create(MediaType.parse("multipart/form-data"), toByteArray(fis));
            return MultipartBody.Part.createFormData("video", file.getName(), requestFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //转二进制
    private static byte[] toByteArray(InputStream input) throws IOException{
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while(-1 != (n = input.read(buffer))){
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
    private static byte[] toByteArray(FileInputStream input) throws IOException{
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while(-1 != (n = input.read(buffer))){
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /*//退出录制
    public void gotoMainActivity(View view){
        //创建一个意图
        Intent intent = new Intent(SimpleVideoRecordActivity.this,MainActivity.class);
        startActivity(intent);
        finish();//结束当前的Activity
        //如果没有上面的finish()，那么当跳转到MainActivity之后，SecondActivity只会onStop,不会ondestroy。即仍然还在栈中
        //需要注意的是，当它跳到MainActivity时，会去重新创建一个新的MainActivity，即执行MainActivity中的onCreate()方法;
    }
*/
}

