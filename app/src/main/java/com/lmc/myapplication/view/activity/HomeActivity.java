package com.lmc.myapplication.view.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import com.lmc.myapplication.R;
import com.lmc.myapplication.base.BaseMvpActivity;
import com.lmc.myapplication.model.CommonHomeModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseMvpActivity<CommonHomeModel> implements NavController.OnDestinationChangedListener {
    public NavController mProjectControl;

    @Override
    public void setUpData() {

    }

    @Override
    public void setUpView() {
        mProjectControl = Navigation.findNavController(this, R.id.project_fragment_control);
        mProjectControl.addOnDestinationChangedListener(this);
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

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        String label = destination.getLabel().toString();
    }
}