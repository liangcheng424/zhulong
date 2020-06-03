package com.lmc.myapplication.base;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lmc.frame.ApiConfig;
import com.lmc.frame.LoadTypeConfig;
import com.lmc.myapplication.interfaces.DataListtener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import javax.security.auth.login.LoginException;

public class BaseActivity extends AppCompatActivity {

    public Application1907 mApplication;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (Application1907) getApplication();
    }

    /**
     * recyclerview在整个项目中使用比较频繁，将公共代码进行抽取
     * @param pRecyclerView 要操作的recyclerview
     * @param pSmartRefreshLayout 如果有刷新和加载更多的问题，所使用的smartRefreshLayout
     * @param pDataListener 刷新和加载的监听，如果实际使用中部涉及到刷新和加载更多，直接传null
     */
    public void initRecyclerView(RecyclerView pRecyclerView, SmartRefreshLayout pSmartRefreshLayout, final DataListtener pDataListener){
        if(pRecyclerView!=null)pRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(pSmartRefreshLayout !=null &&pDataListener!=null){
            pSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    pDataListener.dataType(LoadTypeConfig.MORE);
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    pDataListener.dataType(LoadTypeConfig.REFRESH);
                }
            });
        }
    }

    public void showLog(Object content){
        Log.e("凉城" , content.toString());
    }

    public void showToast(Object content){
        Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
    }
}
