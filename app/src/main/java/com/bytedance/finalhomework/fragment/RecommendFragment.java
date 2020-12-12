package com.bytedance.finalhomework.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bytedance.finalhomework.R;
import com.bytedance.finalhomework.base.BaseFragment;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 暂时放个空Fragment
 */
public class RecommendFragment extends BaseFragment {
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void init() {

    }

}
