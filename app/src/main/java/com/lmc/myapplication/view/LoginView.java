package com.lmc.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.lmc.myapplication.R;

public class LoginView extends RelativeLayout {
    //登录布局s
    Context mContext;
    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.login_view, this);
    }
}
