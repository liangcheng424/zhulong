package com.lmc.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lmc.data.TestInfo;
import com.lmc.frame.ApiConfig;
import com.lmc.frame.CommonPresenter;
import com.lmc.frame.ICommonPresenter;
import com.lmc.frame.ICommonView;
import com.lmc.frame.LoadTypeConfig;
import com.lmc.frame.TestModel;
import com.lmc.frame.utils.ParamHashMap;
import com.lmc.myapplication.adapter.TestAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ICommonView {

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private ICommonPresenter commonPresenter;
    int pageId = 0;
    private TestAdapter testAdapter;
    List<TestInfo.DataInfo> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);

        TestModel testModel = new TestModel();
        commonPresenter = new CommonPresenter(this,testModel);

        final Map<String,Object> params = new ParamHashMap().add("c", "api").add("a", "getList");
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageId ++ ;
                commonPresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.MORE,params,pageId);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageId = 0;
                commonPresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH,params,pageId);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        testAdapter = new TestAdapter(this,datas);
        recyclerView.setAdapter(testAdapter);
        commonPresenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.NORMAL,params,pageId);
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object[] pD) {
        switch (whichApi){
            case ApiConfig.TEST_GET:
                if(loadType == LoadTypeConfig.MORE){
                    refreshLayout.finishLoadMore();
                    datas.clear();

                }else if(loadType == LoadTypeConfig.REFRESH){
                    refreshLayout.finishRefresh();
                }
                List<TestInfo.DataInfo> datas = ((TestInfo)pD[0]).datas;
                MainActivity.this.datas.addAll(datas);
                testAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        Toast.makeText(MainActivity.this, pThrowable.getMessage()!=null ? pThrowable.getMessage():"网络请求发生错误", Toast.LENGTH_SHORT).show();
    }
}
