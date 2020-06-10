package com.lmc.myapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lmc.data.mainpage.BannerLiveInfo;
import com.lmc.myapplication.R;
import com.lmc.myapplication.interfaces.ZLImageLoader;
import com.lmc.myapplication.view.design.RoundImage;
import com.yiyatech.utils.GlideUtil;
import com.yiyatech.utils.newAdd.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder> {
    private Context mContext;
    private List<BannerLiveInfo.Live> liveData;

    public LiveAdapter(Context pContext, List<BannerLiveInfo.Live> pLiveData) {
        mContext = pContext;
        liveData = pLiveData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.live_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BannerLiveInfo.Live liveBean = liveData.get(position);
        GlideUtil.loadImage(holder.roundPhoto,liveBean.teacher_photo);
        holder.title.setText(liveBean.activityName);
        if (!TextUtils.isEmpty(liveBean.startTime))holder.time.setText(TimeUtil.formatLiveTime(Long.parseLong(liveBean.startTime)));
    }

    @Override
    public int getItemCount() {
        return liveData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.round_photo)
        RoundImage roundPhoto;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time_image)
        ImageView timeImage;
        @BindView(R.id.time)
        TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
