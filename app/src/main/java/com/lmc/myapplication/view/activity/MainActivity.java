package com.lmc.myapplication.view.activity;

import androidx.recyclerview.widget.RecyclerView;

import com.lmc.data.TestInfo;
import com.lmc.frame.ApiConfig;
import com.lmc.frame.ICommonModel;
import com.lmc.frame.LoadTypeConfig;
import com.lmc.myapplication.R;
import com.lmc.myapplication.model.TestModel;
import com.lmc.frame.utils.ParamHashMap;
import com.lmc.myapplication.adapter.TestAdapter;
import com.lmc.myapplication.base.BaseMvpActivity;
import com.lmc.myapplication.interfaces.DataListtener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseMvpActivity {

    int pageId = 0;
 //   @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
  //  @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private TestAdapter testAdapter;
    List<TestInfo.DataInfo> datas = new ArrayList<>();
    private Map<String, Object> params;


    @Override
    public void setUpData() {  //whichApi        pPs
        mPresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.NORMAL, params, pageId);
    }

    @Override
    public void setUpView() {
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        params = new ParamHashMap().add("c", "api").add("a", "getList");

        initRecyclerView(recyclerView, refreshLayout, new DataListtener() {
            @Override
            public void dataType(int mode) {
                if(mode == LoadTypeConfig.MORE){
                    pageId ++ ;
                    mPresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.MORE, params,pageId);
                }else{
                    pageId = 0;
                    mPresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH, params,pageId);
                }
            }
        });
        testAdapter = new TestAdapter(this, datas);
        recyclerView.setAdapter(testAdapter);
    }

    @Override
    public ICommonModel setModel() {
        return new TestModel();
    }

    //布局
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.TEST_GET:
                if ((int)pD[0] == LoadTypeConfig.MORE) {
                    refreshLayout.finishLoadMore();
                } else if ((int)pD[0] == LoadTypeConfig.REFRESH) {
                    refreshLayout.finishRefresh();
                    datas.clear();
                }
                List<TestInfo.DataInfo> datas = ((TestInfo) pD[0]).datas;
                MainActivity.this.datas.addAll(datas);
                testAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void netFiled(int whichApi, Throwable pThrowable) {
      //  Toast.makeText(MainActivity.this, pThrowable.getMessage() != null ? pThrowable.getMessage() : "网络请求发生错误", Toast.LENGTH_SHORT).show();
    }
}
