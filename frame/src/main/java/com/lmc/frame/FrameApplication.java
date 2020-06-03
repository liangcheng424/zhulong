package com.lmc.frame;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.lmc.data.Device;
import com.lmc.data.LoginInfo;
import com.lmc.data.SpecialtyChooseEntity;
import com.yiyatech.utils.UtilsApplication;


public class FrameApplication extends UtilsApplication {
    private static FrameApplication application;
    private Device mDeviceInfo;
    private LoginInfo mLoginInfo;
    private String cookie;

    private  SpecialtyChooseEntity.DataBean selectedInfo;

    public SpecialtyChooseEntity.DataBean getSelectedInfo() {
        return selectedInfo;
    }

    public void setSelectedInfo(SpecialtyChooseEntity.DataBean selectedInfo) {
        this.selectedInfo = selectedInfo;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public LoginInfo getLoginInfo() {
        return mLoginInfo;
    }

    public void setLoginInfo(LoginInfo mLoginInfo) {
        this.mLoginInfo = mLoginInfo;
    }

    public Device getDeviceInfo() {
        return mDeviceInfo;
    }

    public void setDeviceInfo(Device mDeviceInfo) {
        this.mDeviceInfo = mDeviceInfo;
    }


    public boolean isLogin(){
        return mLoginInfo!=null&&!TextUtils.isEmpty(mLoginInfo.getUid());
    }
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static FrameApplication getFrameApplication(){
        return application;
    }

    public static Context getFrameApplicationContext(){
        return application.getApplicationContext();
    }
}
