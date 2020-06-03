package com.lmc.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lmc.data.SpecialtyChooseEntity;
import com.lmc.myapplication.R;
import com.lmc.myapplication.view.design.RoundImage;
import com.yiyatech.utils.GlideUtil;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    private List<SpecialtyChooseEntity> mList;
    private Context mContext;

    public SubjectAdapter(List<SpecialtyChooseEntity> pList, Context pContext) {
        mList = pList;
        mContext = pContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.subject_view, parent,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecialtyChooseEntity specialtyChooseEntity = mList.get(position);
        GlideUtil.loadImage(holder.left_round_image, specialtyChooseEntity.getIcon());
        holder.item_title.setText(specialtyChooseEntity.getBigspecialty());
        holder.recycler.setLayoutManager(new GridLayoutManager(mContext, 4));
        holder.recycler.setAdapter(new SubjectChildAdapter(specialtyChooseEntity.getData(),mContext,this));
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundImage left_round_image;
        TextView item_title;
        RecyclerView recycler;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            left_round_image = itemView.findViewById(R.id.left_round_image);
            item_title = itemView.findViewById(R.id.item_title);
            recycler = itemView.findViewById(R.id.recycler);
        }
    }
}
