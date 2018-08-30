package com.comment.tek.interfaces;

/**
 * Created by huanghongfa on 2018/8/23.
 */

public interface IRequestListener<T> {

    void onSuccess(T t);

    void onFailure(Throwable throwable);
}
