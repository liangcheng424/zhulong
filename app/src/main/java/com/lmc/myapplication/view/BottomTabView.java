package com.lmc.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.lmc.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomTabView extends RelativeLayout {

    @BindView(R.id.main_page_tab)
    TextView mainPageTab;
    @BindView(R.id.course_tab)
    TextView courseTab;
    @BindView(R.id.vip_tab)
    TextView vipTab;
    @BindView(R.id.data_tab)
    TextView dataTab;
    @BindView(R.id.mine_tab)
    TextView mineTab;
    private Context mContext;
    private final int mTabNum;
    private List<TextView> usedTabView = new ArrayList<>();//根据自定义属性中tab数量的设置，过滤掉不用的view，剩下接下来要使用的view
    private List<Integer> normalIcon;//未选中的icon
    private List<Integer> selectedIcon;//选中的icon
    private List<String> tabContent;//tab对应的内容
    private int defaultTab = 1;//默认显示第几个tab
    private @ColorInt
    int mSelectedColor;
    private @ColorInt
    int mUnSelectedColor;

    public BottomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.bottom_tab_view, this);
        ButterKnife.bind(this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomTabView, 0, 0);
        mTabNum = ta.getInt(R.styleable.BottomTabView_tabNum, 4);
        mSelectedColor = ta.getColor(R.styleable.BottomTabView_selectedColor, Color.RED);
        mUnSelectedColor = ta.getColor(R.styleable.BottomTabView_unSelectedColor, Color.BLACK);
        Collections.addAll(usedTabView,mainPageTab,courseTab,vipTab,dataTab,mineTab);
        if(mTabNum < 5){
            for (int i = 5; i >mTabNum ; i--) {
                usedTabView.get(i-1).setVisibility(GONE);
                usedTabView.remove(i-1);
            }
        }
    }

    /**
     * 给tab设置数据
     * @param normalIcon  未选中的icon集合
     * @param selectedIcon  选中的icon集合
     * @param tabContent  tab内容
     * @param pDefaultTab  默认选中哪个tab
     */
    public void setResource(List<Integer> normalIcon,List<Integer> selectedIcon,List<String> tabContent,int pDefaultTab){
        //tab数量不能为0，没有意义
        if(pDefaultTab<=0){
            Log.e(this.getClass().getSimpleName(), "tab不能为0个");
        }

        //如果使用的view数量不等于未选中的icon的数量，或者不等于选中的icon的数量或者不等于tab的数量，打印log
        if(usedTabView.size() !=normalIcon.size() || usedTabView.size() !=selectedIcon.size() || usedTabView.size() != tabContent.size()){
            Log.e(this.getClass().getName(),"---------"+"自定义属性的数量和传递的资源数量不匹配");
            return;
        }
        this.normalIcon = normalIcon;
        this.selectedIcon = selectedIcon;
        this.tabContent = tabContent;
        defaultTab = pDefaultTab;
        setContent();
        setStyle();
    }
    //给vie设置颜色和位置
    private void setStyle() {
        for (int i = 0; i < usedTabView.size(); i++) {
            if(i == defaultTab-1){
                usedTabView.get(i).setTextColor(mSelectedColor);
                usedTabView.get(i).setCompoundDrawablesRelativeWithIntrinsicBounds(0, selectedIcon.get(i), 0, 0);
            }else{
                usedTabView.get(i).setTextColor(mUnSelectedColor);
                usedTabView.get(i).setCompoundDrawablesRelativeWithIntrinsicBounds(0, normalIcon.get(i), 0, 0);
            }
        }
    }

    //设置内容
    private void setContent() {
        for (int i = 0; i < tabContent.size(); i++) {
            usedTabView.get(i).setText(tabContent.get(i));
        }
    }

    @OnClick({R.id.main_page_tab, R.id.course_tab, R.id.vip_tab, R.id.data_tab, R.id.mine_tab})
    public void onViewClicked(View view) {
            int id = view.getId();
            if(id == R.id.main_page_tab){
                defaultTab = 1;
            }else if(id == R.id.course_tab){
                defaultTab = 2;
            }else if(id == R.id.vip_tab){
                defaultTab = 3;
            }else if(id == R.id.data_tab){
                defaultTab = 4;
            }else if(id == R.id.mine_tab){
                defaultTab = 5;
            }
            setStyle();
    }
}
