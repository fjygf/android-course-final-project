package com.bytedance.finalhomework.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bytedance.finalhomework.R;
import com.bytedance.finalhomework.activity.PlayListActivity;
import com.bytedance.finalhomework.base.BaseFragment;
import com.bytedance.finalhomework.base.BaseRvAdapter;
import com.bytedance.finalhomework.base.BaseRvViewHolder;
import com.bytedance.finalhomework.base.VideoBean;
import com.bytedance.finalhomework.fragment.CurrentLocationFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//配置recyclerview具体显示内容
public class GridVideoAdapter extends BaseRvAdapter<VideoBean, GridVideoAdapter.GridVideoViewHolder> {

    public GridVideoAdapter(Context context, List<VideoBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(GridVideoViewHolder holder, VideoBean videoBean, int position) {
        Glide.with(context)
                .load(videoBean.getCoverRes())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.ivCover);
        holder.tvContent.setText("#" +videoBean.getUserBean().getNickName()+ "#" + videoBean.getContent());//作者名字+附加信息

        holder.itemView.setOnClickListener(v -> {

            //页面跳转并传递url参数
            Intent intent =new Intent(context, PlayListActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("videourl", "https://sf3-hscdn-tos.pstatp.com/obj/developer-baas/baas/tttyvl/c0fa180944b514a5_1607568068884.mp4");
            bundle.putString("imageurl", "https://sf1-hscdn-tos.pstatp.com/obj/developer-baas/baas/tttyvl/773fc072c5760dca_1607568068597.png");
            bundle.putString("videourl", videoBean.getVideoUrl());
            bundle.putString("imageurl", videoBean.getCoverRes());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public GridVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //绑定一个LinearLayout，包含视频封面，附加内容，可以在附加内容里添加一下用户名
        View view = LayoutInflater.from(context).inflate(R.layout.item_gridvideo, parent, false);
        return new GridVideoViewHolder(view);
    }

    public class GridVideoViewHolder extends BaseRvViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public GridVideoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

