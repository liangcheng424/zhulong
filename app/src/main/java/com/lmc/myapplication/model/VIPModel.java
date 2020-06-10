package com.lmc.myapplication.model;

import android.content.Context;

import com.lmc.frame.ApiConfig;
import com.lmc.frame.ICommonModel;
import com.lmc.frame.ICommonPresenter;
import com.lmc.frame.NetManger;
import com.lmc.myapplication.R;
import com.lmc.myapplication.base.Application1907;

public class VIPModel implements ICommonModel {
    NetManger manger = NetManger.getInstance();
    Context context = Application1907.get07ApplicationContext();
    @Override
    public void getData(ICommonPresenter presenter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.VIP_BANNER:
                manger.netWork(manger.getService(context.getString(R.string.edu_openapi)).getVipBanner(), presenter, whichApi);
                break;
            case ApiConfig.VIP_LIST:
                manger.netWork(manger.getService(context.getString(R.string.edu_openapi)).getVipList(), presenter, whichApi);
                break;
        }
    }
}
