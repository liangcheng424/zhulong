package com.lmc.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lmc.data.mainpage.BannerLiveInfo;
import com.lmc.data.mainpage.IndexCommondEntity;
import com.lmc.myapplication.R;
import com.yiyatech.utils.GlideUtil;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageAdapter extends RecyclerView.Adapter {
    private List<IndexCommondEntity> bottomList;
    private List<BannerLiveInfo.Carousel> bannerData;
    private List<BannerLiveInfo.Live> liveData;
    private Context mContext;
    private final int BANNER = 1, LABEL = 2, LIVE = 3, RIGHT_IMAGE = 4, BIG_IMAGE = 5;

    public MainPageAdapter(List<IndexCommondEntity> pBottomList, List<BannerLiveInfo.Carousel> pBannerData, List<BannerLiveInfo.Live> pLiveData, Context pContext) {
        bottomList = pBottomList;
        bannerData = pBannerData;
        liveData = pLiveData;
        mContext = pContext;
    }

    @Override
    public int getItemViewType(int position) {
       int type = RIGHT_IMAGE;
        if (bannerData != null && bannerData.size() != 0 && position == 0) {
            type = BANNER;
        } else if (position == 1) {
            type = LABEL;
        } else if (liveData != null && liveData.size() != 0 && position == 2) {
            type = LIVE;
        } else {
            int currentPos = liveData!=null&&liveData.size()!=0?position-3:position-2;
            if (bottomList != null && bottomList.size() != 0) {
                if (bottomList.get(currentPos).type == 3) {
                    type = RIGHT_IMAGE;
                } else {
                    type = BIG_IMAGE;
                }
            }
        }
        return type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            View root = LayoutInflater.from(mContext).inflate(R.layout.item_homepage_ad, parent, false);
            return new BannerViewHolder(root);
        } else if (viewType == LABEL) {
            View root = LayoutInflater.from(mContext).inflate(R.layout.item_homepage_shortcuts, parent, false);
            return new LabelViewHolder(root);
        } else if (viewType == LIVE) {//recleview，
            View root = LayoutInflater.from(mContext).inflate(R.layout.live_recycler_item, parent, false);
            return new LiveViewHolder(root);
        } else if (viewType == RIGHT_IMAGE) {
            View root = LayoutInflater.from(mContext).inflate(R.layout.item_homepage_post, parent, false);
            return new RightViewHolder(root);
        } else {//大图
            View root = LayoutInflater.from(mContext).inflate(R.layout.main_page_list_two_item, parent, false);
            return new BigViewHolder(root);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.banner.setImages(bannerData).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    BannerLiveInfo.Carousel ban = (BannerLiveInfo.Carousel) path;
                    Glide.with(mContext).load(ban.getImg()).into(imageView);
                }
            }).start();
        } else if(itemViewType==LABEL){

        }else if (itemViewType == LIVE) {
            LiveViewHolder liveViewHolder = (LiveViewHolder) holder;
            LiveAdapter liveAdapter = new LiveAdapter(mContext,liveData);
            liveViewHolder.recycler.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL,false));
            liveViewHolder.recycler.setAdapter(liveAdapter);
        }else {
            if (bottomList.size()==0)return;
            IndexCommondEntity indexCommondEntity = bottomList.get(liveData == null || liveData.size() == 0 ? position - 2 : position - 3);
            if (itemViewType == RIGHT_IMAGE){
                RightViewHolder rightViewHolder = (RightViewHolder) holder;
                rightViewHolder.tvTitle.setText(indexCommondEntity.getTitle());
                rightViewHolder.tvAuthorAndTime.setText(indexCommondEntity.getDate());
                rightViewHolder.tvBrowseNum.setText(indexCommondEntity.getView_num() + "人浏览");
                rightViewHolder.tvCommentNum.setText(indexCommondEntity.getReply_num() + "人跟帖");
                GlideUtil.loadCornerImage(((RightViewHolder) holder).ivPhoto,indexCommondEntity.getPic(),null,6);
            } else {
                BigViewHolder bigViewHolder = (BigViewHolder) holder;
                bigViewHolder.tvLearnNum.setText(indexCommondEntity.getView_num() + "人学习");
                bigViewHolder.tvLikeNum.setText(indexCommondEntity.getRate() + "好评率");
                bigViewHolder.tvTitle.setText(indexCommondEntity.getTitle());
                RoundedCorners roundedCorners = new RoundedCorners(20);
                RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners);
                GlideUtil.loadCornerImage(((BigViewHolder) holder).ivPhoto,indexCommondEntity.getPic(),null,6);
            }
        }
    }

    @Override
    public int getItemCount() {
        return liveData != null && liveData.size() != 0 ? bottomList.size() + 3 : bottomList.size() + 2;
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner banner;

        public BannerViewHolder(View pRoot) {
            super(pRoot);
            ButterKnife.bind(this, pRoot);
        }
    }

    public class LabelViewHolder extends RecyclerView.ViewHolder {
        public LabelViewHolder(View pRoot) {
            super(pRoot);
          //  ButterKnife.bind(this, pRoot);
        }
    }

    public class LiveViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycler)
        RecyclerView recycler;
        public LiveViewHolder(View pRoot) {
            super(pRoot);
            ButterKnife.bind(this, pRoot);
        }
    }

    public class RightViewHolder extends RecyclerView.ViewHolder {
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

        public RightViewHolder(View pRoot) {
            super(pRoot);
            ButterKnife.bind(this, pRoot);
        }
    }

    public class BigViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.cv_photo)
        CardView cvPhoto;
        @BindView(R.id.tv_learn_num)
        TextView tvLearnNum;
        @BindView(R.id.tv_like_num)
        TextView tvLikeNum;

        public BigViewHolder(View pRoot) {
            super(pRoot);
            ButterKnife.bind(this, pRoot);
        }
    }
}
