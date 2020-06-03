package com.lmc.myapplication.model;

import android.content.Context;
import android.text.TextUtils;

import com.lmc.frame.ApiConfig;
import com.lmc.frame.ICommonModel;
import com.lmc.frame.ICommonPresenter;
import com.lmc.frame.NetManger;
import com.lmc.frame.utils.ParamHashMap;
import com.lmc.myapplication.R;
import com.lmc.myapplication.base.Application1907;

public class LaunchModel implements ICommonModel {
    NetManger manger = NetManger.getInstance();
    Context context = Application1907.get07ApplicationContext();
    @Override
    public void getData(ICommonPresenter presenter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.ADVERT:
                ParamHashMap map = new ParamHashMap()
                        .add("w", params[1])
                        .add("h", params[2])
                        .add("positions_id", "APP_QD_01")
                        .add("is_show", 0);
                if(!TextUtils.isEmpty((String) params[0]))map.add("specialty_id",params[0]);
                manger.netWork(manger.getService(context.getString(R.string.ad_openapi)).getAdvert(map),presenter,whichApi);
                break;
                case ApiConfig.SUBJECT:
                    manger.netWork(manger.getService(context.getString(R.string.edu_openapi)).getSubjectList(), presenter, whichApi);
                    break;
        }
    }
}
