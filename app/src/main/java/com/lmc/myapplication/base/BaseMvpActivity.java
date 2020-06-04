package com.lmc.myapplication.base;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.lmc.frame.CommonPresenter;
import com.lmc.frame.ICommonModel;
import com.lmc.frame.ICommonView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpActivity<M extends ICommonModel> extends BaseActivity implements ICommonView {

    private M mModel;
    public CommonPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        mModel = setModel();
        mPresenter = new CommonPresenter(this,mModel);        //presenter = initPresenter();

        setUpView();   //initView();

        setUpData();   //initData();
    }

    public abstract void setUpData();

    public abstract void setUpView();

    public abstract M setModel();

    public abstract int getLayoutId();

    public abstract void netSuccess(int whichApi, Object[] pD);

    public void netFiled(int whichApi, Throwable pThrowable){}
    @Override
    public void onSuccess(int whichApi, Object[] pD) {
        netSuccess(whichApi,pD);
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        showLog("net work error"+whichApi+"error message"+pThrowable!=null&&!TextUtils.isEmpty(pThrowable.getMessage())?pThrowable.getMessage():"不明错误类型");
        netFiled(whichApi,pThrowable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }
}
