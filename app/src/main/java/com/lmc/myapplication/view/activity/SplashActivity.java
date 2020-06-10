package com.lmc.myapplication.view.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import com.lmc.data.BaseInfo;
import com.lmc.data.LoginInfo;
import com.lmc.data.MainAdEntity;
import com.lmc.data.SpecialtyChooseEntity;
import com.lmc.frame.ApiConfig;
import com.lmc.frame.ICommonModel;
import com.lmc.frame.constants.ConstantKey;
import com.lmc.frame.secret.SystemUtils;
import com.lmc.myapplication.R;
import com.lmc.myapplication.base.BaseSplashActivity;
import com.lmc.myapplication.model.LaunchModel;
import com.yiyatech.utils.GlideUtil;
import com.yiyatech.utils.newAdd.SharedPrefrenceUtils;
import java.util.concurrent.TimeUnit;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import static com.lmc.myapplication.constants.JumpConstant.*;

public class SplashActivity extends BaseSplashActivity {

    private BaseInfo<MainAdEntity> mInfo;
    private Disposable subscribe;
    private SpecialtyChooseEntity.DataBean selectedInfo;

    @Override
    public void setUpData() {
        selectedInfo = SharedPrefrenceUtils.getObject(this, ConstantKey.SUBJECT_SELECT);
        String specialtyId = "";
        if(selectedInfo !=null && !TextUtils.isEmpty(selectedInfo.getSpecialty_id())){
            mApplication.setSelectedInfo(selectedInfo);
            specialtyId = selectedInfo.getSpecialty_id();
        }
        Point realSize = SystemUtils.getRealSize(this);
        //specialtyId  广告id      x 宽  y 高 屏幕宽高
        mPresenter.getData(ApiConfig.ADVERT, specialtyId,realSize.x,realSize.y);

        new Handler().postDelayed(()->{ if (mInfo == null)jump(); },3000);
        LoginInfo loginInfo = SharedPrefrenceUtils.getObject(this,ConstantKey.LOGIN_INFO);
        if (loginInfo != null && !TextUtils.isEmpty(loginInfo.getUid()))mApplication.setLoginInfo(loginInfo);

    }

    @Override
    public void setUpView() {
        advertImage = findViewById(R.id.advert_image);
        timeView = findViewById(R.id.time_view);
        //让window进行全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initDevice();
    }

    @Override
    public ICommonModel setModel() {
        return new LaunchModel();
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
        mInfo = (BaseInfo<MainAdEntity>) pD[0];
        GlideUtil.loadImage(advertImage, mInfo.result.getInfo_url());
        timeView.setVisibility(View.VISIBLE);
        goTime();
    }

    private int preTime = 4;
    private void goTime() {
        subscribe = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (preTime - aLong > 0) timeView.setText("跳过 " + (preTime - aLong) + "s");
                    else jump();
                });
    }
    private void jump() {
        if(subscribe!=null)subscribe.dispose();
        if(selectedInfo!=null &&!TextUtils.isEmpty(selectedInfo.getSpecialty_id())){
            if(mApplication.isLogin()){
                startActivity(new Intent(this,HomeActivity.class));
            }else{
                startActivity(new Intent(this,LoginActivity.class).putExtra(JUMP_KEY, SPLASH_TO_LOGIN));
            }
        }else{
            startActivity(new Intent(this,SubjectActivity.class).putExtra(JUMP_KEY, SPLASH_TO_SUB));
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscribe !=null &&subscribe.isDisposed()) subscribe.dispose();
    }

    @OnClick({R.id.advert_image, R.id.time_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.advert_image:
                if(mInfo!=null){
                    //  mInfo.result.getJump_url();
                }
                break;
            case R.id.time_view:
                jump();
                break;
        }
    }
}