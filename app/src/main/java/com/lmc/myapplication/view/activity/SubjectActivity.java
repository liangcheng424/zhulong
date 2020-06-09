package com.lmc.myapplication.view.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lmc.data.BaseInfo;
import com.lmc.data.InfoEvent;
import com.lmc.data.SpecialtyChooseEntity;
import com.lmc.frame.ApiConfig;
import com.lmc.frame.constants.ConstantKey;
import com.lmc.myapplication.R;
import com.lmc.myapplication.adapter.SubjectAdapter;
import com.lmc.myapplication.base.BaseMvpActivity;
import com.lmc.myapplication.model.LaunchModel;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SubjectActivity extends BaseMvpActivity<LaunchModel> {

    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.more_content)
    TextView moreContent;
    private SubjectAdapter subjectAdapter;
    private List<SpecialtyChooseEntity> mListData = new ArrayList<>();

    @Override
    public void setUpData() {
        if (SharedPrefrenceUtils.getSerializableList(this, ConstantKey.SUBJECT_LIST) != null) {
            mListData.addAll(SharedPrefrenceUtils.<SpecialtyChooseEntity>getSerializableList(this, ConstantKey.SUBJECT_LIST));
        } else {
            mPresenter.getData(ApiConfig.SUBJECT);
        }
    }

    @Override
    public void setUpView() {
        titleContent.setText(getString(R.string.select_subject));
        recycler.setLayoutManager(new LinearLayoutManager(this));
        subjectAdapter = new SubjectAdapter(mListData, this);
        recycler.setAdapter(subjectAdapter);
        moreContent.setText("完成");
        moreContent.setOnClickListener(v->startActivity(new Intent(SubjectActivity.this,mApplication.isLogin() ? HomeActivity.class : LoginActivity.class)));
    }

    @Override
    public LaunchModel setModel() {
        return new LaunchModel();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_subject;
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.SUBJECT:
                BaseInfo<List<SpecialtyChooseEntity>> info = (BaseInfo<List<SpecialtyChooseEntity>>) pD[0];
                mListData.addAll(info.result);
                subjectAdapter.notifyDataSetChanged();
                SharedPrefrenceUtils.putSerializableList(this, ConstantKey.SUBJECT_LIST, mListData);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPrefrenceUtils.putObject(this, ConstantKey.SUBJECT_SELECT, mApplication.getSelectedInfo());
        EventBus.getDefault().post(new InfoEvent());
    }

    @OnClick(R.id.back_image)
    public void onViewClicked() {
        finish();
    }
}