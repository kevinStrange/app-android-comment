package com.comment.tek.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comment.tek.activity.R;
import com.comment.tek.base.BaseFragment;

/**
 * Created by huanghongfa on 2018/8/24.
 * 首页
 */

public class HomePageFragment extends BaseFragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }

    }

}
