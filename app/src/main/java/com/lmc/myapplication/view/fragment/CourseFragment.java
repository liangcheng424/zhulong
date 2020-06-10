package com.lmc.myapplication.view.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.lmc.frame.constants.ConstantKey;
import com.lmc.myapplication.R;
import com.lmc.myapplication.adapter.MyFragmentAdapter;
import com.lmc.myapplication.base.BaseMvpFragment;
import com.lmc.myapplication.model.CourseModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class CourseFragment extends BaseMvpFragment<CourseModel> {

    @BindView(R.id.slide_layout)
    SlidingTabLayout slideLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private MyFragmentAdapter mMyFragmentAdapter;

    @Override
    public CourseModel setModel() {
        return null;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    public void setUpView() {
        mMyFragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(), mFragments, titleList);
        viewPager.setAdapter(mMyFragmentAdapter);
        slideLayout.setViewPager(viewPager);
    }

    @Override
    public void setUpData() {
        Collections.addAll(titleList, "训练营", "精品课", "公开课");
        Collections.addAll(mFragments, CourseChildFragment.getInstance(ConstantKey.TRAINTAB),CourseChildFragment.getInstance(ConstantKey.BESTTAB),CourseChildFragment.getInstance(ConstantKey.PUBLICTAB));
        mMyFragmentAdapter.notifyDataSetChanged();;
        slideLayout.notifyDataSetChanged();
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {

    }
}