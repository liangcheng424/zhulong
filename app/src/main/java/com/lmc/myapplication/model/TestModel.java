package com.lmc.myapplication.model;

import com.lmc.frame.ICommonModel;
import com.lmc.frame.ICommonPresenter;
import com.lmc.frame.NetManger;
import java.util.Map;


public class TestModel implements ICommonModel {

    NetManger mManger = NetManger.getInstance();
    @Override
    public void getData(final ICommonPresenter presenter, final int whichApi, Object[] params) {
        final int loadType = (int) params[0];
        Map param = (Map) params[1];
        int pageId = (int) params[2];
        mManger.netWork(mManger.getService().getTestData(param, pageId), presenter, whichApi, loadType);
    }

}
