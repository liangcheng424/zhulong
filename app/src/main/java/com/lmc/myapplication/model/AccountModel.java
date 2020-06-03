package com.lmc.myapplication.model;

import android.content.Context;

import com.lmc.frame.ApiConfig;
import com.lmc.frame.FrameApplication;
import com.lmc.frame.ICommonModel;
import com.lmc.frame.ICommonPresenter;
import com.lmc.frame.NetManger;
import com.lmc.frame.utils.ParamHashMap;
import com.lmc.myapplication.R;
import com.lmc.myapplication.base.Application1907;

public class AccountModel implements ICommonModel {
    private NetManger manger = NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();
    @Override
    public void getData(ICommonPresenter presenter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.SEND_VERIFY:
                manger.netWork(manger.getService(mContext.getString(R.string.passport_openapi_user)).getLoginVerify((String)params[0]),presenter, whichApi);
                break;
            case ApiConfig.VERIFY_LOGIN:
                ParamHashMap map = new ParamHashMap();
                        map.add("mobile", params[0]);
                        map.add("code", params[1]);
                manger.netWork(manger.getService(mContext.getString(R.string.passport_openapi_user)).loginByVerify(new ParamHashMap().add("mobile", params[0]).add("code", params[1])),presenter,whichApi);
                break;
            case ApiConfig.GET_HEADER_INFO:
                String uid = FrameApplication.getFrameApplication().getLoginInfo().getUid();
                ParamHashMap map1 = new ParamHashMap();
                map1.add("zuid", uid);
                map1.add("uid",uid);
                manger.netWork(manger.getService(mContext.getString(R.string.passport_api)).getHeaderInfo(map1), presenter,whichApi);
                break;
        }
    }
}
