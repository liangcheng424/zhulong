package com.lmc.myapplication.view.fragment;

import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import com.lmc.data.BaseInfo;
import com.lmc.data.DataGroupListEntity;
import com.lmc.frame.ApiConfig;
import com.lmc.frame.FrameApplication;
import com.lmc.frame.LoadTypeConfig;
import com.lmc.myapplication.R;
import com.lmc.myapplication.adapter.DataGroupAdapter;
import com.lmc.myapplication.base.BaseMvpFragment;
import com.lmc.myapplication.interfaces.DataListtener;
import com.lmc.myapplication.interfaces.OnRecyclerItemClick;
import com.lmc.myapplication.model.DataModel;
import com.lmc.myapplication.view.activity.LoginActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import static com.lmc.myapplication.adapter.DataGroupAdapter.FOCUS_TYPE;
import static com.lmc.myapplication.constants.JumpConstant.*;


public class DataGroupFragment extends BaseMvpFragment<DataModel> implements DataListtener, OnRecyclerItemClick {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private List<DataGroupListEntity> mList = new ArrayList<>();
    private DataGroupAdapter mDataGroupAdapter;

    public static DataGroupFragment newInstance() {
        DataGroupFragment fragment = new DataGroupFragment();
        return fragment;
    }

    @Override
    protected void setUpView() {
        initRecyclerView(recyclerView, refreshLayout, this);
        mDataGroupAdapter = new DataGroupAdapter(getActivity(),mList);
        recyclerView.setAdapter(mDataGroupAdapter);
        mDataGroupAdapter.setOnRecyclerItemClick(this);
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
        mPresenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.NORMAL,page);
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi){
            case ApiConfig.DATA_GROUP:
                BaseInfo<List<DataGroupListEntity>> info = (BaseInfo<List<DataGroupListEntity>>) pD[0];
                if (info.isSuccess()) {
                    List<DataGroupListEntity> result = info.result;
                    showLog(result.size());
                    int loadMode = (int)pD[1];
                    if (loadMode == LoadTypeConfig.REFRESH) {
                        refreshLayout.finishRefresh();
                        mList.clear();
                    } else if (loadMode == LoadTypeConfig.MORE){
                        refreshLayout.finishLoadMore();
                    }
                    mList.addAll(result);
                    mDataGroupAdapter.notifyDataSetChanged();
                }
                break;
            case ApiConfig.CLICK_CANCEL_FOCUS:
                BaseInfo baseInfo = (BaseInfo) pD[0];
                int clickPos = (int) pD[1];
                if(baseInfo.isSuccess()){
                    mList.get(clickPos).setIs_ftop(0);
                    mDataGroupAdapter.notifyItemChanged(clickPos);
                }
                break;
            case ApiConfig.CLICK_TO_FOCUS:
                BaseInfo base = (BaseInfo) pD[0];
                int clickJoinPos = (int) pD[1];
                if(base.isSuccess()){
                    mList.get(clickJoinPos).setIs_ftop(1);
                    mDataGroupAdapter.notifyItemChanged(clickJoinPos);
                }
                break;
        }
    }

    @Override
    public void dataType(int mode) {
        if(mode== LoadTypeConfig.REFRESH){
            mPresenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.REFRESH,1);
        }else{
            page++;
            mPresenter.getData(ApiConfig.DATA_GROUP, LoadTypeConfig.MORE,page);
        }
    }

    @Override
    public void onItemClick(int pos, Object[] pObjects) {
        switch ((int)pObjects[0]){
            case FOCUS_TYPE:
                boolean login = FrameApplication.getFrameApplication().isLogin();
                if(login){
                    DataGroupListEntity dataGroupListEntity = mList.get(pos);
                    if(dataGroupListEntity.isFocus()){
                        mPresenter.getData(ApiConfig.CLICK_CANCEL_FOCUS,dataGroupListEntity.getGid(),pos);
                    }else{
                        mPresenter.getData(ApiConfig.CLICK_TO_FOCUS, dataGroupListEntity.getGid(),dataGroupListEntity.getGroup_name(),pos);
                    }
                }else{
                    startActivity(new Intent(getActivity(), LoginActivity.class).putExtra(JUMP_KEY, DATAGROUPFRAGMENT_TO_LOGIN));
                }
                break;
        }
    }
}