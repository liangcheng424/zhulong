package com.lmc.myapplication.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import com.lmc.data.BaseInfo;
import com.lmc.data.LoginInfo;
import com.lmc.data.PersonHeader;
import com.lmc.frame.ApiConfig;
import com.lmc.frame.constants.ConstantKey;
import com.lmc.myapplication.R;
import com.lmc.myapplication.base.BaseMvpActivity;
import com.lmc.myapplication.model.AccountModel;
import com.lmc.myapplication.view.LoginView;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import static com.lmc.myapplication.constants.JumpConstant.*;

public class LoginActivity extends BaseMvpActivity<AccountModel> implements LoginView.LoginViewCallBack {

    @BindView(R.id.login_view)
    LoginView loginView;
    private String phoneNum;
    private Disposable subscribe;
    private String mFrom;

    @Override
    public void setUpData() {

    }

    @Override
    public void setUpView() {
        mFrom = getIntent().getStringExtra(JUMP_KEY);
        loginView.setLoginViewCallBack(this);
    }

    @Override
    public AccountModel setModel() {
        return new AccountModel();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        switch (whichApi) {
            case ApiConfig.SEND_VERIFY: //发送验证码
                BaseInfo<String> info = (BaseInfo<String>) pD[0];
          //      showToast(info.result);
                if(info.isSuccess()){
                    goTime();
                }else{
                 showToast("验证码发送太频繁，请稍后重试");
                }
                break;
            case ApiConfig.VERIFY_LOGIN: //验证码登录
                BaseInfo<LoginInfo> baseInfo = (BaseInfo<LoginInfo>) pD[0];
                LoginInfo loginInfo = baseInfo.result;
                loginInfo.login_name = phoneNum;
                mApplication.setLoginInfo(loginInfo);
                mPresenter.getData(ApiConfig.GET_HEADER_INFO);
                break;
            case ApiConfig.GET_HEADER_INFO:
                PersonHeader personHeader = ((BaseInfo<PersonHeader>)pD[0]).result;
                mApplication.getLoginInfo().personHeader = personHeader;
                SharedPrefrenceUtils.putObject(this, ConstantKey.LOGIN_INFO, mApplication.getLoginInfo());
                jump();
                break;
        }
    }

    private void jump() {
        if(mFrom.equals(SPLASH_TO_LOGIN) || mFrom.equals(SUB_TO_LOGIN)){
            startActivity(new Intent(this,HomeActivity.class));
        }
        finish();
    }
    private long time = 60l;//l  代表long
    private void goTime() {
       subscribe = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    loginView.getVerifyCode.setText(time - aLong + "s");
                    if (time - aLong < 1) doPre();
                });
    }

    private void doPre(){
        if(subscribe !=null && !subscribe.isDisposed()) subscribe.dispose();
        loginView.getVerifyCode.setText("获取验证码");
    }
    @Override
    public void sendVerifyCode(String phoneNum) {
        this.phoneNum = phoneNum;
        mPresenter.getData(ApiConfig.SEND_VERIFY, phoneNum);
    }

    @Override
    public void loginPress(int type, String username, String password) {
        doPre();
        if(loginView.mCurrentLoginType ==loginView.VERIFY_TYPE){
            mPresenter.getData(ApiConfig.VERIFY_LOGIN, username,password);
        }
    }

    @OnClick({R.id.close_login, R.id.register_press, R.id.forgot_pwd, R.id.login_by_qq, R.id.login_by_wx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_login:
                if(!TextUtils.isEmpty(mFrom)&&(mFrom.equals(SUB_TO_LOGIN)||mFrom.equals(SPLASH_TO_LOGIN))){
                    startActivity(new Intent(this,HomeActivity.class));
                }
                finish();
                break;
            case R.id.register_press:
                break;
            case R.id.forgot_pwd:
                break;
            case R.id.login_by_qq:
                break;
            case R.id.login_by_wx:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doPre();
    }
}