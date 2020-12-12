package com.bytedance.finalhomework.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bytedance.finalhomework.R;
import com.bytedance.finalhomework.base.BaseActivity;
import com.bytedance.finalhomework.base.CommPagerAdapter;
import com.bytedance.finalhomework.fragment.MainFragment;

import java.util.ArrayList;

import butterknife.BindView;
import rx.functions.Action1;
//主页面
public class MainActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;//通过ButterKnife绑定的格式，ViewPager如其名所述，是负责翻页的一个 View
    /*
     * ViewPager 通过 setAdapter() 来建立与 PagerAdapter 的联系。这个联系是双向的，
     * 一方面，ViewPager 会拥有PagerAdapter 对象，从而可以在需要时调用 PagerAdapter 的方法；
     * 另一方面，ViewPager 会在 setAdapter() 中调用 PagerAdapter 的 registerDataSetObserver() 方法，
     * 注册一个自己生成的 PagerObserver 对象，从而在 PagerAdapter 有所需要时（如notifyDataSetChanged()或
     * notifyDataSetInvalidated() 时），可以调用 Observer 的 onChanged() 或 onInvalidated() 方法，
     * 从而实现 PagerAdapter 向 ViewPager 方向发送信息。
     */
    private CommPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();//包括mainFragment和personalHomeFragment
    public static int curMainPage;
    private MainFragment mainFragment = new MainFragment();//主Fragment（0）。里面包含了海淀和推荐页面
//    private PersonalHomeFragment personalHomeFragment = new PersonalHomeFragment();//个人主页（1）
    /** 上次点击返回键时间 */
    private long lastTime;
    /** 连续按返回键退出时间 */
    private final int EXIT_TIME = 2000;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        //下面三行代码取消顶部的app名称
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        fragments.add(mainFragment);
//        fragments.add(personalHomeFragment);
        pagerAdapter = new CommPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"",""});
        viewPager.setAdapter(pagerAdapter);

//        //点击头像切换页面
//        //暂时不要
//        RxBus.getDefault().toObservable(MainPageChangeEvent.class)
//                .subscribe((Action1<MainPageChangeEvent>) event -> {
//                    if (viewPager != null) {
//                        viewPager.setCurrentItem(event.getPage());
//                    }
//                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curMainPage = position;

//                if (position == 0) {
//                    RxBus.getDefault().post(new PauseVideoEvent(true));
//                } else if (position == 1){
//                    RxBus.getDefault().post(new PauseVideoEvent(false));
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        //双击返回退出App
        if (System.currentTimeMillis() - lastTime > EXIT_TIME) {
            if (viewPager.getCurrentItem() == 1) {
                viewPager.setCurrentItem(0);
            }else{
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                lastTime = System.currentTimeMillis();
            }
        } else {
            super.onBackPressed();
        }
    }

    //前往录制
    public void gotoSimpleVideoRecordActivity(View view){

        //创建一个意图
        Intent intent = new Intent(MainActivity.this, SimpleVideoRecordActivity.class);
        startActivity(intent);

    }
}