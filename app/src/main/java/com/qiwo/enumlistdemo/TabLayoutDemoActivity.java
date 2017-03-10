package com.qiwo.enumlistdemo;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by l on 2016/7/18.
 */
public class TabLayoutDemoActivity extends BaseActivity {

    @Bind(R.id.tablayout)
    TabLayout tablayout;

    @Bind(R.id.viewpager)
    ViewPager viewpager;

    String[] tabArray = new String[]{"要闻", "英雄联盟", "守望先锋", "NBA", "程序员", "电竞", "经济"};

    @Override
    int getLayoutId() {
        return R.layout.activity_tablayout;
    }

    @Override
    protected void initView() {
        super.initView();
        // 设置tab的名字
        for (String title : tabArray
             ) {
            tablayout.addTab(tablayout.newTab().setText(title));
        }
        // 设置tab的滚动样式
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        // 初始化Fragmentlist
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < 7; i++
             ) {
            if(i == 0){
                Fragment fragment = new TabLayoutFragment();
                fragmentList.add(fragment);
                continue;
            }
            Fragment fragment = new SimpleFragment(tabArray[i]);
            fragmentList.add(fragment);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this, fragmentList, tabArray);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
    }

    public static class ViewPagerAdapter extends FragmentStatePagerAdapter{

        String[] tabArray;
        Context context;
        List<Fragment> fragmentList;
        public ViewPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList, String[] tabArray) {
            super(fm);
            this.tabArray = tabArray;
            this.context = context;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return tabArray.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabArray[position % tabArray.length];
        }
    }
}
