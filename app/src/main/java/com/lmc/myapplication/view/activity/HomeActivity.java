package com.lmc.myapplication.view.activity;

import android.os.Bundle;

import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.lmc.myapplication.R;
import com.lmc.myapplication.base.BaseMvpActivity;
import com.lmc.myapplication.model.CommonHomeModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseMvpActivity<CommonHomeModel> {
    public NavController mProjectControl;

    @Override
    public void setUpData() {

    }

    @Override
    public void setUpView() {
        mProjectControl = Navigation.findNavController(this,R.id.project_fragment_control);
    }

    @Override
    public CommonHomeModel setModel() {
        return new CommonHomeModel();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }
}