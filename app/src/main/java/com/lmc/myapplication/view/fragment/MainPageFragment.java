package com.lmc.myapplication.view.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lmc.data.BaseInfo;
import com.lmc.data.SpecialtyChooseEntity;
import com.lmc.data.mainpage.BannerLiveInfo;
import com.lmc.data.mainpage.IndexCommondEntity;
import com.lmc.frame.ApiConfig;
import com.lmc.frame.LoadTypeConfig;
import com.lmc.myapplication.R;
import com.lmc.myapplication.adapter.MainPageAdapter;
import com.lmc.myapplication.base.Application1907;
import com.lmc.myapplication.base.BaseMvpFragment;
import com.lmc.myapplication.interfaces.DataListtener;
import com.lmc.myapplication.model.MainPageModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainPageFragment extends BaseMvpFragment<MainPageModel> implements DataListtener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private MainPageAdapter mMainPageAdapter;
    int page = 1;
    private List<IndexCommondEntity> bottomList = new ArrayList<>();
    private List<BannerLiveInfo.Carousel> bannerData = new ArrayList<>();
    private List<BannerLiveInfo.Live> liveData = new ArrayList<>();
    private String mSpecialty_id;

    @Override
    public MainPageModel setModel() {
        return new MainPageModel();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_main_page;
    }

    @Override
    public void setUpView() {
        SpecialtyChooseEntity.DataBean selectedInfo = Application1907.getFrameApplication().getSelectedInfo();
        mSpecialty_id = selectedInfo.getSpecialty_id();
        initRecyclerView(recyclerView, refreshLayout, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainPageAdapter = new MainPageAdapter(bottomList,bannerData,liveData,getActivity());
        recyclerView.setAdapter(mMainPageAdapter);
    }

    @Override
    public void setUpData() {
        mPresenter.getData(ApiConfig.MAIN_PAGE_LIST, LoadTypeConfig.NORMAL,page);
        mPresenter.getData(ApiConfig.MAIN_PAGE_BANNER, LoadTypeConfig.NORMAL);
    }

    private boolean mainList = false,banLive = false;
    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.MAIN_PAGE_LIST:
                int loadType = (int)pD[1];
                BaseInfo<List<IndexCommondEntity>> baseInfo = (BaseInfo<List<IndexCommondEntity>>) pD[0];
                if(baseInfo.isSuccess()){
                    if(loadType == LoadTypeConfig.MORE)refreshLayout.finishLoadMore();
                    if(loadType==LoadTypeConfig.REFRESH){
                        bottomList.clear();
                        refreshLayout.finishRefresh();
                    }
                    bottomList.addAll(baseInfo.result);
                    mainList = true;
                    if(banLive){
                        mMainPageAdapter.notifyDataSetChanged();
                        mainList = false;
                    }
                }else showToast("列表加载错误");
                break;
            case ApiConfig.MAIN_PAGE_BANNER:
                JsonObject jsonObject = (JsonObject) pD[0];
                try {
                    JSONObject object = new JSONObject(jsonObject.toString());
                    if(object.getString("errNo").equals("0")){
                        int load = (int)pD[1];
                        if(load==LoadTypeConfig.REFRESH){
                            bannerData.clear();
                            liveData.clear();
                            refreshLayout.finishRefresh();
                        }
                        String result = object.getString("result");
                        JSONObject resultObject = new JSONObject(result);
                        String live = resultObject.getString("live");
                        if(live.equals("true")||live.equals("false")){
                            resultObject.remove("live");
                        }
                        result = resultObject.toString();
                        BannerLiveInfo info = new Gson().fromJson(result, BannerLiveInfo.class);
                        for(BannerLiveInfo.Carousel data :info.Carousel){
                            bannerData.add(data);
                        }
                       // liveData.addAll(info.live);
                        if(info.live != null )liveData.addAll(info.live);
                        banLive = true;
                        if(mainList){
                            mMainPageAdapter.notifyDataSetChanged();
                            banLive = false;
                        }
                    }
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void dataType(int mode) {
        if(mode==LoadTypeConfig.REFRESH){
            mainList = false;
            banLive = false;
            mPresenter.getData(ApiConfig.MAIN_PAGE_LIST, LoadTypeConfig.REFRESH,1);
            mPresenter.getData(ApiConfig.MAIN_PAGE_BANNER, LoadTypeConfig.REFRESH);
        }else{
            page++;
            mPresenter.getData(ApiConfig.MAIN_PAGE_LIST, LoadTypeConfig.MORE,page);
        }
    }
}