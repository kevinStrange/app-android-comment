package com.comment.tek.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comment.banner.interfaces.IOnBannerListener;
import com.comment.banner.util.BannerConfig;
import com.comment.banner.view.Banner;
import com.comment.tek.activity.R;
import com.comment.tek.base.BaseApplication;
import com.comment.tek.base.BaseFragment;
import com.comment.tek.widget.GlideImageLoader;

/**
 * Created by huanghongfa on 2018/8/24.
 * 首页
 */

public class HomePageFragment extends BaseFragment implements View.OnClickListener, IOnBannerListener {


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
        Banner banner2 = findViewById(R.id.banner2);
        banner2.setImages(BaseApplication.banner_images)
                .setBannerTitles(BaseApplication.banner_titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }

    }

    @Override
    public void OnBannerClick(int position) {
        toast("点击了" + position);
    }
}
