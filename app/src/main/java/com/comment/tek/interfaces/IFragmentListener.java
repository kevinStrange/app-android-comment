package com.comment.tek.interfaces;

/**
 * Created by huanghongfa on 2018/8/23.
 */

public interface IFragmentListener {

    //fragment界面刷新
    void refresh();

    //fragment界面初始化
    void initView();

    //fragment界面是否初始化完成
    boolean isInitialize();
}
