package com.qiwo.enumlistdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by l on 2016/7/18.
 */
public class SimpleFragment extends Fragment {
    @Nullable

    String name;
    @Bind(R.id.tv_content)
    TextView tvContent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_simple, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        tvContent.setText(name);
    }

    public SimpleFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public SimpleFragment(String name) {
        super();
        this.name = name;
    }

}
