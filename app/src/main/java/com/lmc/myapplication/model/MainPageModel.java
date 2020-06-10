package com.lmc.myapplication.model;

import android.content.Context;

import com.lmc.frame.ApiConfig;
import com.lmc.frame.FrameApplication;
import com.lmc.frame.Host;
import com.lmc.frame.ICommonModel;
import com.lmc.frame.ICommonPresenter;
import com.lmc.frame.NetManger;
import com.lmc.frame.constants.Constants;
import com.lmc.frame.utils.ParamHashMap;
import com.lmc.myapplication.base.Application1907;
import com.lmc.myapplication.constants.Method;

public class MainPageModel implements ICommonModel {
    NetManger mNetManger = NetManger.getInstance();
    Context context = Application1907.get07ApplicationContext();
    @Override
    public void getData(ICommonPresenter presenter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.MAIN_PAGE_BANNER:
                ParamHashMap live = new ParamHashMap()
                        .add("pro", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id())
                        .add("more_live","1")
                        .add("is_new",1)
                        .add("new_banner",1);
                mNetManger.netWork(NetManger.mService.getBannerLive(Host.EDU_OPENAPI+Method.GETCAROUSELPHOTO,live),presenter,whichApi,params[0]);
                break;
            case ApiConfig.MAIN_PAGE_LIST:
                ParamHashMap add = new ParamHashMap()
                        .add("specialty_id", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id())
                        .add("page", params[1])
                        .add("limit", Constants.LIMIT_NUM)
                        .add("new_banner", 1);
                mNetManger.netWork(NetManger.mService.getMainPageList(Host.EDU_OPENAPI+ Method.GETINDEXCOMMEND,add),presenter,whichApi,params[0]);
             /*   int loadType = (int) params[0];
                Map<String,Object> paramHashMap = (ParamHashMap) params[1];
                mNetManger.netWork( mNetManger.getService(context.getString(R.string.edu_openapi)).getMainPageList(paramHashMap),presenter, whichApi,loadType);*/
                break;
        }


    }
}

