package com.comment.tek.net;

import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 *
 * 父类的网络请求
 * Created by huanghongfa on 2018/8/23.
 */

public abstract class BaseNetRequest {

    public static final String HTTP_POST = "POST";
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_GET = "GET";
    public static final String HTTP_DELETE = "DELETE";

    public List<String> list = new ArrayList<>();

    public BaseNetRequest() {

    }

    public abstract  <T> Observable<T> reQuest(HttpMethod method, String url, Type type, Class<T> clazz, HttpParams params, HttpHeaders headers);


//    public abstract  <T> Observable<T> reQuest(Context context, RequestEntity entity, Type type,Class<T> clazz, boolean isShowDialog, String requestHint);

    public abstract String getTag();

    public abstract String urlAppendToken(String url);
}
