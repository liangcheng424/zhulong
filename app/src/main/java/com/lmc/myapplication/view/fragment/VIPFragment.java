package com.lmc.myapplication.view.fragment;

import android.content.Context;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lmc.data.vip.VipBannerBean;
import com.lmc.data.vip.VipListBean;
import com.lmc.frame.ApiConfig;
import com.lmc.myapplication.R;
import com.lmc.myapplication.adapter.VipAdapter;
import com.lmc.myapplication.base.BaseMvpFragment;
import com.lmc.myapplication.model.VIPModel;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import butterknife.BindView;

public class VIPFragment extends BaseMvpFragment<VIPModel> {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.banner)
    Banner banner;
    private VipAdapter mVipAdapter;

    @Override
    protected void setUpView() {
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mVipAdapter = new VipAdapter(getActivity());
        recycler.setAdapter(mVipAdapter);
    }

    @Override
    public VIPModel setModel() {
        return new VIPModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_v_i_p;
    }

    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.VIP_BANNER);
        mPresenter.getData(ApiConfig.VIP_LIST);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.VIP_BANNER:
                List<VipBannerBean.ResultBean.LunbotuBean> lunbotu = ((VipBannerBean) pD[0]).getResult().getLunbotu();
                banner.setImages(lunbotu).setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        VipListBean.ResultBean.ListBean ban = (VipListBean.ResultBean.ListBean) path;
                        Glide.with(getActivity()).load(((VipListBean.ResultBean.ListBean) path).getThumb()).into(imageView);
                    }
                }).start();
                break;
            case ApiConfig.VIP_LIST:
                List<VipListBean.ResultBean.ListBean> list = ((VipListBean) pD[0]).getResult().getList();
                mVipAdapter.setData(list);
                break;
        }
    }
}