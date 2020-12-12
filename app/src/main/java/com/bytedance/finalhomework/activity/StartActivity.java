package com.bytedance.finalhomework.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.bytedance.finalhomework.R;
import com.bytedance.finalhomework.base.BaseActivity;

//启动页面
public class StartActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void init() {
        setFullScreen();
        //下面三行代码取消顶部的app名称
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        new CountDownTimer(500, 500) {//millisInFuture:倒计时的总时长,countDownInterval：每次的间隔时间  单位都是毫秒
            @Override
            ////这个是每次间隔指定时间的回调，millisUntilFinished：剩余的时间，单位毫秒
            public void onTick(long millisUntilFinished) {

            }

            @Override
            //这个是倒计时结束的回调
            //结束后来到main页面
            public void onFinish() {
                startActivity(new Intent(StartActivity.this,MainActivity.class));
                finish();
            }
        }.start();
    }
}