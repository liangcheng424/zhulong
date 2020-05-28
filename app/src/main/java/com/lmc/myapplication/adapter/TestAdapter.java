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

import com.bumptech.glide.Glide;
import com.lmc.data.TestInfo;
import com.lmc.myapplication.R;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    Context context;
    List<TestInfo.DataInfo> datas;

    public TestAdapter(Context context, List<TestInfo.DataInfo> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.test_item, parent,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestInfo.DataInfo dataInfo = datas.get(position);
        holder.desc.setText(!TextUtils.isEmpty(dataInfo.description) ? dataInfo.description : !TextUtils.isEmpty(dataInfo.author) ? dataInfo.author : dataInfo.title);
        holder.title.setText(dataInfo.title);
        Glide.with(context).load(dataInfo.thumbnail).into(holder.leftImage);
    }

    @Override
    public int getItemCount() {
        return datas!=null ? datas.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView leftImage;
        TextView title;
        TextView desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            leftImage = itemView.findViewById(R.id.left_image);
            title = itemView.findViewById(R.id.title_content);
            desc = itemView.findViewById(R.id.desc_content);
        }
    }
}
