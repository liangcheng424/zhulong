package com.lmc.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lmc.data.RecentlyBestEntity;
import com.lmc.myapplication.R;
import com.yiyatech.utils.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentlyBestAdapter extends RecyclerView.Adapter<RecentlyBestAdapter.ViewHolder> {
    Context mContext;
    List<RecentlyBestEntity> mList;

    public RecentlyBestAdapter(Context pContext, List<RecentlyBestEntity> pList) {
        mContext = pContext;
        mList = pList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recently_best_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentlyBestEntity entity = mList.get(position);
        holder.tvTitle.setText(entity.getTitle());
        holder.tvAuthorAndTime.setText(entity.getCreate_time());
        holder.tvBrowseNum.setText(entity.getView_num()+"人浏览");
        holder.tvCommentNum.setText(entity.getReply_num()+"人跟帖");
        GlideUtil.loadCornerImage(holder.ivPhoto, entity.getPic(),null,6f);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_browse_num)
        TextView tvBrowseNum;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_author_and_time)
        TextView tvAuthorAndTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
