package com.qiwo.enumlistdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by l on 2016/7/19.
 */
public class TabLayoutFragment extends Fragment {

    View rootView;
    @Bind(R.id.tab_fragment)
    TabLayout tab_Fragment;

    @Bind(R.id.viewpager)
    ViewPager viewpager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_tablayout, null);
            ButterKnife.bind(this, rootView);
            initView();
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    String[] titles = new String[]{"深圳", "上海"};

    private void initView() {
        for (String title : titles
             ) {
            tab_Fragment.addTab(tab_Fragment.newTab().setText(title));
        }

        tab_Fragment.setTabMode(TabLayout.MODE_FIXED);
        List<Fragment> listFragment = new ArrayList<>();

        for (int i = 0; i < 2 ; i++){
            listFragment.add(new SimpleFragment(titles[i]));
        }

        TabLayoutDemoActivity.ViewPagerAdapter adapter = new TabLayoutDemoActivity.ViewPagerAdapter(
                getActivity().getSupportFragmentManager(),
                getActivity(),
                listFragment,
                titles
        );
        viewpager.setAdapter(adapter);
        tab_Fragment.setupWithViewPager(viewpager);
    }

}
