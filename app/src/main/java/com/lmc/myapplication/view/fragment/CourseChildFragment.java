package com.lmc.myapplication.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lmc.data.BaseInfo;
import com.lmc.data.CourseListInfo;
import com.lmc.data.SearchItemEntity;
import com.lmc.frame.ApiConfig;
import com.lmc.frame.LoadTypeConfig;
import com.lmc.myapplication.R;
import com.lmc.myapplication.adapter.CourseChildAdapter;
import com.lmc.myapplication.base.Application1907;
import com.lmc.myapplication.base.BaseMvpFragment;
import com.lmc.myapplication.interfaces.DataListtener;
import com.lmc.myapplication.model.CourseModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseChildFragment extends BaseMvpFragment<CourseModel> implements DataListtener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int mIndex;
    private int page = 1;
    private List<SearchItemEntity> mList = new ArrayList<>();
    private CourseChildAdapter mCourseChildAdapter;

    public static CourseChildFragment getInstance(int index) {
        CourseChildFragment courseChildFragment = new CourseChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("whichFragment", index);
        courseChildFragment.setArguments(bundle);
        return courseChildFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndex = (int) getArguments().get("whichFragment");
    }

    @Override
    public CourseModel setModel() {
        return new CourseModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public void setUpView() {
        mCourseChildAdapter = new CourseChildAdapter(mList, getActivity());
        recyclerView.setAdapter(mCourseChildAdapter);
        initRecyclerView(recyclerView, refreshLayout, this);
    }

    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.COURSE_TYPE, LoadTypeConfig.NORMAL,mIndex,page);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.COURSE_TYPE:
                BaseInfo<CourseListInfo> baseInfo = (BaseInfo<CourseListInfo>) pD[0];
                if(baseInfo.isSuccess()){
                    List<SearchItemEntity> lists = baseInfo.result.lists;
                    int load = (int) pD[1];
                    if(load==LoadTypeConfig.REFRESH){
                        refreshLayout.finishRefresh();
                        mList.clear();
                    }else{
                        refreshLayout.finishLoadMore();
                    }
                    mList.addAll(lists);
                    mCourseChildAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void dataType(int mode) {
        if(mode==LoadTypeConfig.REFRESH){
            mPresenter.getData(ApiConfig.COURSE_TYPE, LoadTypeConfig.REFRESH,mIndex,1);
        }else{
            page++;
            mPresenter.getData(ApiConfig.COURSE_TYPE, LoadTypeConfig.REFRESH,mIndex,page);
        }
    }
}
