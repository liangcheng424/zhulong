package com.lmc.myapplication.view.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.lmc.data.BaseInfo;
import com.lmc.data.RecentlyBestEntity;
import com.lmc.frame.ApiConfig;
import com.lmc.frame.LoadTypeConfig;
import com.lmc.myapplication.R;
import com.lmc.myapplication.adapter.RecentlyBestAdapter;
import com.lmc.myapplication.base.BaseMvpFragment;
import com.lmc.myapplication.interfaces.DataListtener;
import com.lmc.myapplication.model.DataModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecentlyBestFragment extends BaseMvpFragment<DataModel> implements DataListtener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    List<RecentlyBestEntity> mList = new ArrayList<>();
    private RecentlyBestAdapter mRecentlyBestAdapter;
    int page = 1;

    public static RecentlyBestFragment newInstance() {
        RecentlyBestFragment fragment = new RecentlyBestFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    protected void setUpView() {
        initRecyclerView(recyclerView,refreshLayout,this);
        mRecentlyBestAdapter = new RecentlyBestAdapter(getActivity(), mList);
        recyclerView.setAdapter(mRecentlyBestAdapter);
    }

    @Override
    public DataModel setModel() {
        return new DataModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.DATA_SMART, LoadTypeConfig.NORMAL,page);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.DATA_SMART:
                BaseInfo<List<RecentlyBestEntity>> baseInfo = (BaseInfo<List<RecentlyBestEntity>>) pD[0];
                if(baseInfo.isSuccess()){
                    List<RecentlyBestEntity> result = baseInfo.result;
                    int load = (int)pD[1];
                    if(load==LoadTypeConfig.REFRESH){
                        mList.clear();
                        refreshLayout.finishRefresh();
                    }else if(load==LoadTypeConfig.MORE){
                        refreshLayout.finishLoadMore();
                    }
                    mList.addAll(result);
                    mRecentlyBestAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void dataType(int mode) {
        if(mode==LoadTypeConfig.REFRESH){
            mPresenter.getData(ApiConfig.DATA_SMART, LoadTypeConfig.REFRESH,1);
        }else{
            page++;
            mPresenter.getData(ApiConfig.DATA_SMART, LoadTypeConfig.MORE,page);
        }
    }
}