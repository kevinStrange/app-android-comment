package com.comment.tek.manage;

import android.content.Context;

import com.comment.tek.bean.LoginBean;
import com.comment.tek.interfaces.ILoginListener;
import com.comment.tek.interfaces.IRequestListener;
import com.comment.tek.net.BaseNetRequestApi;

/**
 * Created by huanghongfa on 2018/8/23.
 */

public class LoginManage {


    public void doLogin(Context context, String username, String password, final ILoginListener listener) {
        BaseNetRequestApi.getInstance().doLogin(context, username, password, new IRequestListener<LoginBean>() {
            @Override
            public void onSuccess(LoginBean loginBean) {
                listener.onSuccess(loginBean);
            }

            @Override
            public void onFailure(Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }
}
