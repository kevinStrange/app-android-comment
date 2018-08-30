package com.comment.tek.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.comment.tek.interfaces.IFragmentListener;

/**
 * Created by huanghongfa on 2018/8/24.
 */

public class BaseFragment extends Fragment implements IFragmentListener {
    public Context context;
    public View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 刷新界面
     */
    @Override
    public void refresh() {

    }

    /**
     * 初始化UI
     */
    @Override
    public void initView() {

    }

    /**
     * 是否加载完数据
     *
     * @return
     */
    @Override
    public boolean isInitialize() {
        return false;
    }


    /**
     * 界面刷新数据
     */

    public void updateView() {

    }

    protected void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    protected void startActivity(Class clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }


    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id) {
        if (view == null) {
            throw new RuntimeException("view is null");
        }
        return (T) view.findViewById(id);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
