package com.lmc.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lmc.data.vip.VipListBean;
import com.lmc.myapplication.R;
import com.yiyatech.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VipAdapter extends RecyclerView.Adapter<VipAdapter.ViewHolder> {
    public Context mContext;
    List<VipListBean.ResultBean.ListBean> data = new ArrayList<>();

    public VipAdapter(Context pContext) {
        mContext = pContext;
    }

    public void setData(List<VipListBean.ResultBean.ListBean> pData) {
        data = pData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.vip_list_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VipListBean.ResultBean.ListBean listBean = data.get(position);
        holder.vipStudentNumber.setText(listBean.getStudentnum() + "人学习");
        holder.vipTitle.setText(listBean.getLesson_name());
        holder.commentRate.setText(listBean.getComment_rate()+"好评");
        GlideUtil.loadCornerImage(holder.vipImg, listBean.getThumb(), null, 6);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vip_img)
        ImageView vipImg;
        @BindView(R.id.vip_title)
        TextView vipTitle;
        @BindView(R.id.vip_student_number)
        TextView vipStudentNumber;
        @BindView(R.id.comment_rate)
        TextView commentRate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
