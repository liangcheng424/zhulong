package com.lmc.myapplication.model;

import android.content.Context;

import com.lmc.frame.ApiConfig;
import com.lmc.frame.FrameApplication;
import com.lmc.frame.Host;
import com.lmc.frame.ICommonModel;
import com.lmc.frame.ICommonPresenter;
import com.lmc.frame.NetManger;
import com.lmc.frame.utils.ParamHashMap;
import com.lmc.myapplication.R;
import com.lmc.myapplication.base.Application1907;
import com.lmc.myapplication.constants.Method;

public class DataModel implements ICommonModel {

    private NetManger manger = NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();

    @Override
    public void getData(ICommonPresenter presenter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.DATA_GROUP:
                ParamHashMap add = new ParamHashMap()
                        .add("type", 1)
                        .add("fid", FrameApplication.getFrameApplication().getSelectedInfo()
                                .getFid()).add("page", params[1]);
                NetManger.getInstance().netWork(NetManger.mService.getGroupList(Host.BBS_OPENAPI+ Method.GETGROUPLIST,add),presenter,whichApi,params[0]);
                break;
                case ApiConfig.CLICK_CANCEL_FOCUS:
                    ParamHashMap add1 = new ParamHashMap().add("group_id", params[0]).add("type", 1).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                    NetManger.getInstance().netWork(NetManger.mService.removeFocus(Host.BBS_API+Method.REMOVEGROUP,add1),presenter,whichApi,params[1]);
                    break;
            case ApiConfig.CLICK_TO_FOCUS:
                ParamHashMap add2 = new ParamHashMap().add("gid", params[0]).add("group_name", params[1]).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                NetManger.getInstance().netWork(NetManger.mService.focus(Host.BBS_API+Method.JOINGROUP,add2),presenter,whichApi,params[2]);
                break;
            case ApiConfig.DATA_SMART:
                ParamHashMap add3 = new ParamHashMap()
                        .add("page", params[1])
                        .add("fid", FrameApplication.getFrameApplication().getSelectedInfo().getFid());
                NetManger.getInstance().netWork(NetManger.mService.getRecentlyBestList(Host.BBS_OPENAPI+Method.GETTHREADESSENCE,add3), presenter, whichApi, params[0]);
                break;
        }
    }
}
