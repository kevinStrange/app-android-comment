package com.comment.tek.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by huanghongfa on 2018/8/23.
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {


    public Context mContext;//只要继承基类BaseActivity的子类都可以调用使用

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        setContentView(getView());
        initObj();
        initView();
        initData();
    }

    /**
     * 获取layout布局资源文件
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化对象
     */
    protected abstract void initObj();

    /**
     * 初始化view ID
     */
    protected abstract void initView();

    /**
     * 做初始化数据加载
     */
    protected abstract void initData();

    /**
     * 点击事件
     *
     * @param view
     */
    protected abstract void otherViewClick(View view);

    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }


    protected void setOnClickListener(View.OnClickListener onClickListener, int... ids) {
        for (int id : ids) {
            super.findViewById(id).setOnClickListener(onClickListener);
        }
    }

    protected void setOnClickListener(View.OnClickListener onClickListener, View... views) {
        for (View v : views) {
            v.setOnClickListener(onClickListener);
        }
    }

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void toastLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void toast(String msg, int duration) {
        Toast.makeText(this, msg, duration).show();
    }

    protected void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    private View getView() {
        return View.inflate(this, getLayoutId(), null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                otherViewClick(v);
                break;
        }
    }
}
