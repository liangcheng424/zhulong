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
import com.lmc.myapplication.R;
import com.lmc.myapplication.base.Application1907;
import com.lmc.myapplication.constants.Method;

public class CourseModel implements ICommonModel {

    NetManger mNetManger = NetManger.getInstance();
    private Context mContext = Application1907.get07ApplicationContext();
    private String subjectId = FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id();
    @Override
    public void getData(ICommonPresenter presenter, int whichApi, Object[] params) {
        switch (whichApi){
            case ApiConfig.COURSE_TYPE:
                ParamHashMap add = new ParamHashMap().add("specialty_id", subjectId).add("page", params[2]).add("limit", Constants.LIMIT_NUM).add("course_type", params[1]);
                NetManger.getInstance().netWork(NetManger.mService.getCourseChildData(Host.EDU_OPENAPI+ Method.GETLESSONLISTFORAPI,add),presenter,whichApi,params[0]);
              break;
        }
    }
}
