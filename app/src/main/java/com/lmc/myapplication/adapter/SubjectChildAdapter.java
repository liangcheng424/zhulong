package com.lmc.myapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lmc.data.SpecialtyChooseEntity;
import com.lmc.frame.FrameApplication;
import com.lmc.myapplication.R;

import java.util.List;

public class SubjectChildAdapter extends RecyclerView.Adapter<SubjectChildAdapter.ViewHolder> {
    List<SpecialtyChooseEntity.DataBean> data;
    Context context;
    private SubjectAdapter subjectAdapter;
    public SubjectChildAdapter(List<SpecialtyChooseEntity.DataBean> data, Context context,SubjectAdapter subjectAdapter) {
        this.data = data;
        this.context = context;
        this.subjectAdapter = subjectAdapter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.subject_child_view, parent,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SpecialtyChooseEntity.DataBean dataBean = data.get(position);
        holder.title.setText(dataBean.getSpecialty_name());

        if(FrameApplication.getFrameApplication().getSelectedInfo()!=null
                &&!TextUtils.isEmpty(FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id())
                &&FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id().equals(dataBean.getSpecialty_id())){
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.title.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_subject_selected));
        }else{
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.fontColor333));
            holder.title.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_radius_white_bg));
        }
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameApplication.getFrameApplication().setSelectedInfo(dataBean);
                subjectAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
