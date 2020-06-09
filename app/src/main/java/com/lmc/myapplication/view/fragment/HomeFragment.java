package com.lmc.myapplication.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.lmc.data.InfoEvent;
import com.lmc.data.SpecialtyChooseEntity;
import com.lmc.myapplication.R;
import com.lmc.myapplication.base.Application1907;
import com.lmc.myapplication.base.BaseMvpFragment;
import com.lmc.myapplication.model.MainPageModel;
import com.lmc.myapplication.view.BottomTabView;
import com.lmc.myapplication.view.activity.SubjectActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseMvpFragment implements BottomTabView.OnBottomTabClickCallBack, NavController.OnDestinationChangedListener {

    @BindView(R.id.select_subject)
    TextView selectSubject;
    @BindView(R.id.top_ll)
    LinearLayout topLl;
    private List<Integer> normalIcon = new ArrayList<>();//为选中的icon
    private List<Integer> selectedIcon = new ArrayList<>();// 选中的icon
    private List<String> tabContent = new ArrayList<>();//tab对应的内容
    private final int MAIN_PAGE = 1, COURSE = 2, VIP = 3, DATA = 4, MINE = 5;
    private NavController mHomeController;
    private BottomTabView mTabView;

    @Override
    public MainPageModel setModel() {
        return null;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void setUpView() {
        EventBus.getDefault().register(this);
        mTabView = getView().findViewById(R.id.bottom_tab);
        Collections.addAll(normalIcon, R.drawable.main_page_view, R.drawable.course_view, R.drawable.vip_view, R.drawable.data_view, R.drawable.mine_view);
        Collections.addAll(selectedIcon, R.drawable.main_selected, R.drawable.course_selected, R.drawable.vip_selected, R.drawable.data_selected, R.drawable.mine_selected);
        Collections.addAll(tabContent, "主页", "课程", "VIP", "资料", "我的");
        mTabView.setResource(normalIcon, selectedIcon, tabContent);
        mTabView.setOnBottomTabClickCallBack(this);
        selectSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), SubjectActivity.class), 200);
            }
        });

        SpecialtyChooseEntity.DataBean selectedInfo = Application1907.getFrameApplication().getSelectedInfo();
        selectSubject.setText(selectedInfo.getSpecialty_name());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(InfoEvent pInfoEvent){
        SpecialtyChooseEntity.DataBean selectedInfo = Application1907.getFrameApplication().getSelectedInfo();
        selectSubject.setText(selectedInfo.getSpecialty_name());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setUpData() {
        mHomeController = Navigation.findNavController(getView().findViewById(R.id.home_fragment_container));
        mHomeController.addOnDestinationChangedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        normalIcon.clear();
        selectedIcon.clear();
        tabContent.clear();
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        showLog(destination.getLabel().toString());
        if(destination.getLabel().equals("MineFragment")){
            topLl.setVisibility(View.GONE);
        }else{
            topLl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void clickTab(int tab) {
        switch (tab) {
            case MAIN_PAGE:
                mHomeController.navigate(R.id.mainPageFragment);
                break;
            case COURSE:
                mHomeController.navigate(R.id.courseFragment);
                break;
            case VIP:
                mHomeController.navigate(R.id.vipFragment);
                break;
            case DATA:
                mHomeController.navigate(R.id.dataFragment);
                break;
            case MINE:
                mHomeController.navigate(R.id.mineFragment);
                break;
        }
    }
}