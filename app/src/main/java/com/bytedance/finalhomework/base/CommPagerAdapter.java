package com.bytedance.finalhomework.base;

import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class CommPagerAdapter extends FragmentStatePagerAdapter {
    //该 PagerAdapter 的实现将只保留当前页面，当页面离开视线后，就会被消除，释放其资源
    private ArrayList<? extends Fragment> items;
    private String[] mTitles;

    public CommPagerAdapter(FragmentManager fm, ArrayList< ? extends Fragment> items, String[] mTitles) {
        super(fm);
        this.items = items;
        this.mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return items.size()==0 ? 0 :items.size();
    }

    @Override
    //生成新的Fragment 对象
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;

    }
    @Override
    public Parcelable saveState() {
        return null;
    }
}
