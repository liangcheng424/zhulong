package com.lmc.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lmc.data.DataGroupListEntity;
import com.lmc.myapplication.R;
import com.lmc.myapplication.interfaces.OnRecyclerItemClick;
import com.yiyatech.utils.GlideUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataGroupAdapter extends RecyclerView.Adapter<DataGroupAdapter.ViewHolder> {
    private Context mContext;
    private List<DataGroupListEntity> mList;
    public static final int ITEM_TYPE=1,FOCUS_TYPE=2;

    public DataGroupAdapter(Context pContext, List<DataGroupListEntity> pList) {
        mContext = pContext;
        mList = pList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_group_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataGroupListEntity entity = mList.get(position);
        holder.tvName.setText(entity.getGroup_name());
        holder.tvNumber.setText(entity.getMember_num());
        holder.tvDesc.setText(entity.getIntroduce());
        GlideUtil.loadCornerImage(holder.ivThumb,entity.getAvatar(),null,6);
        holder.tvFocus.setText(entity.isFocus()?"已关注":"关注");
        holder.tvFocus.setSelected(entity.isFocus()?true:false);
        holder.tvFocus.setTextColor(entity.isFocus()? ContextCompat.getColor(mContext,R.color.red):ContextCompat.getColor(mContext, R.color.fontColorGray));
        holder.tvFocus.setOnClickListener(v -> {
            mOnRecyclerItemClick.onItemClick(position, FOCUS_TYPE);
        });
        holder.itemView.setOnClickListener(v -> {
            if(mOnRecyclerItemClick!=null){
                mOnRecyclerItemClick.onItemClick(position, ITEM_TYPE);
            }
        });
    }

    private OnRecyclerItemClick mOnRecyclerItemClick;

    public void setOnRecyclerItemClick(OnRecyclerItemClick pOnRecyclerItemClick){
        mOnRecyclerItemClick = pOnRecyclerItemClick;
    }
    private static final String TAG = "DataGroupAdapter";
    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_focus)
        TextView tvFocus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
