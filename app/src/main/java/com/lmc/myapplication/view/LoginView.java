package com.lmc.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;

import com.lmc.myapplication.R;
import com.lmc.myapplication.interfaces.MyTextWatcher;
import com.yiyatech.utils.ext.ToastUtils;
import com.yiyatech.utils.newAdd.RegexUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends RelativeLayout {
    @BindView(R.id.account_login)
    TextView accountLogin;
    @BindView(R.id.verify_login)
    TextView verifyLogin;
    @BindView(R.id.account_point)
    View accountPoint;
    @BindView(R.id.verify_point)
    View verifyPoint;
    @BindView(R.id.more_type_group)
    Group moreTypeGroup;
    @BindView(R.id.account_name)
    EditText accountName;
    @BindView(R.id.account_secrete)
    EditText accountSecrete;
    @BindView(R.id.account_module)
    ConstraintLayout accountModule;
    @BindView(R.id.area_code)
    TextView areaCode;
    @BindView(R.id.verify_account_first_cut_line)
    View verifyAccountFirstCutLine;
    @BindView(R.id.verify_name)
    EditText verifyName;
    @BindView(R.id.verify_vertical_cut_line)
    View verifyVerticalCutLine;
    @BindView(R.id.verify_code)
    EditText verifyCode;
    @BindView(R.id.get_verify_code)
    public TextView getVerifyCode;
    @BindView(R.id.verify_area)
    ConstraintLayout verifyArea;
    @BindView(R.id.login_press)
    TextView loginPress;
    @BindView(R.id.delete_account_name)
    ImageView deleteAccountName;
    @BindView(R.id.is_visible)
    ImageView isVisible;
    private Context mContext;
    public final int ACCOUNT_TYPE = 1, VERIFY_TYPE = 2;
    public int mCurrentLoginType = ACCOUNT_TYPE;
    private final boolean mIsMoreType;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.login_view, this);
        ButterKnife.bind(this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoginView, 0, 0);
        mIsMoreType = ta.getBoolean(R.styleable.LoginView_isMoreType, true);
        initView();
        if (!mIsMoreType) {
            moreTypeGroup.setVisibility(GONE);
        }
    }

    private void initView() {
        loginPress.setEnabled(false);
        accountName.addTextChangedListener(new MyTextWatcher() {
            @Override
            protected void onMyTextChanged(CharSequence s, int start, int before, int count) {
                deleteAccountName.setVisibility(s.length() == 0 ? INVISIBLE :VISIBLE);
            }
        });
        accountSecrete.addTextChangedListener(new MyTextWatcher() {
            @Override
            protected void onMyTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 5 && !TextUtils.isEmpty(accountName.getText().toString().trim())) {
                    loginPress.setEnabled(true);
                } else loginPress.setEnabled(false);
                isVisible.setVisibility(s.length() > 0 ? VISIBLE : INVISIBLE);
            }
        });

        verifyCode.addTextChangedListener(new MyTextWatcher() {
            @Override
            protected void onMyTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 3 && RegexUtil.isPhone(verifyName.getText().toString().trim())) {
                    loginPress.setEnabled(true);
                } else loginPress.setEnabled(false);
            }
        });
    }

    @OnClick({R.id.account_login, R.id.verify_login, R.id.get_verify_code, R.id.login_press,R.id.delete_account_name,R.id.is_visible})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete_account_name:   // 点击账号登录输入的用户名被清除
                accountName.setText("");
                break;
            case R.id.is_visible:   // 密码那里的小眼睛  默认密码看不见
                accountSecrete.setTransformationMethod(isVisible.isSelected() ? PasswordTransformationMethod.getInstance(): HideReturnsTransformationMethod.getInstance());
                isVisible.setSelected(!isVisible.isSelected());
                break;
            case R.id.account_login: //账号登录
                accountLogin.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                accountPoint.setVisibility(VISIBLE);
                verifyLogin.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
                verifyPoint.setVisibility(INVISIBLE);
                accountModule.setVisibility(VISIBLE);
                verifyArea.setVisibility(INVISIBLE);
                mCurrentLoginType = ACCOUNT_TYPE;
                break;
            case R.id.verify_login:  //验证码登录
                verifyLogin.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                accountLogin.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
                accountPoint.setVisibility(INVISIBLE);
                accountModule.setVisibility(INVISIBLE);
                verifyPoint.setVisibility(VISIBLE);
                verifyArea.setVisibility(VISIBLE);
                verifyPoint.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
                mCurrentLoginType = VERIFY_TYPE;
                break;
            case R.id.get_verify_code:  //获取验证码
                if (TextUtils.isEmpty(verifyName.getText().toString())) {
                    ToastUtils.show(mContext, "用户名为空");
                    return;
                }

                if (!RegexUtil.isPhone(verifyName.getText().toString().trim())) {
                    ToastUtils.show(mContext, "手机号格式错误");
                    return;
                }

                if (loginViewCallBack != null) {
                    loginViewCallBack.sendVerifyCode(areaCode.getText().toString() + verifyName.getText().toString().trim());
                    ;
                }
                break;
            case R.id.login_press:  //登录按钮
                Toast.makeText(mContext, "123", Toast.LENGTH_SHORT).show();
                String username = "", password = "";
                if (mCurrentLoginType == ACCOUNT_TYPE) {
                    username = accountName.getText().toString().trim();
                    password = accountSecrete.getText().toString().trim();
                } else {
                    username = verifyName.getText().toString().trim();
                    password = verifyCode.getText().toString().trim();
                }
                Log.i("Tag", username);
                if (loginViewCallBack != null) {
                    Log.i("Tag", username);
                    loginViewCallBack.loginPress(mCurrentLoginType, username, password);
                }
                break;
        }
    }

    LoginViewCallBack loginViewCallBack;

    public void setLoginViewCallBack(LoginViewCallBack loginViewCallBack) {
        this.loginViewCallBack = loginViewCallBack;
    }

    public interface LoginViewCallBack {
        void sendVerifyCode(String phoneNum);

        void loginPress(int type, String username, String password);
    }
}
