package com.lmc.myapplication.view.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lmc.frame.ApiConfig;
import com.lmc.myapplication.R;
import com.lmc.myapplication.adapter.MyFragmentAdapter;
import com.lmc.myapplication.base.BaseMvpFragment;
import com.lmc.myapplication.model.DataModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class DataFragment extends BaseMvpFragment<DataModel> {

    @BindView(R.id.tab_layout)
    SegmentTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private MyFragmentAdapter mMyFragmentAdapter;

    @Override
    protected void setUpView() {
        Collections.addAll(mFragments, DataGroupFragment.newInstance(),RecentlyBestFragment.newInstance());
        String[] strings = {"资料小组","最新精华"};
        mMyFragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(), mFragments, Arrays.asList(strings));
        viewPager.setAdapter(mMyFragmentAdapter);
        tabLayout.setTabData(strings);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public DataModel setModel() {
        return null;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_data;
    }


    @Override
    public void setUpData() {
    }

    @Override
    public void netSuccess(int whichApi, Object[] pD) {
    }
}