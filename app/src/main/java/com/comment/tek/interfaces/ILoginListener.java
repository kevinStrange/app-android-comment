package com.comment.tek.interfaces;


import com.comment.tek.bean.LoginBean;

/**
 * Created by huanghongfa on 2018/8/23.
 */

public interface ILoginListener {

    void onSuccess(LoginBean loginBean);

    void onFailure(Throwable throwable);
}
