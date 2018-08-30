package com.comment.tek.net;

import android.content.Context;

import com.comment.tek.bean.LoginBean;
import com.comment.tek.bean.LzyResponse;
import com.comment.tek.interfaces.IRequestListener;
import com.comment.tek.manage.UrlManage;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 这里实现相关请求方法
 * Created by huanghongfa on 2018/8/23.
 */

public class BaseNetRequestApi extends BaseNetRequestImp {


    private static BaseNetRequestApi api;

    public static BaseNetRequestApi getInstance() {
        if (null == api) {
            api = new BaseNetRequestApi();
        }
        return api;
    }


    public void doLogin(Context context, String username, String password, IRequestListener<LoginBean> callBack) {
        Type type = new TypeToken<LzyResponse<LoginBean>>() {
        }.getType();
        HttpParams params = new HttpParams();
        params.put("username", username);
        params.put("password", password);
        request(context, HttpMethod.POST, type, UrlManage.API_LOGIN, true, params, callBack);
    }

    public void doLoginToJson(Context context, String username, String password, IRequestListener<LoginBean> callBack) {
        Type type = new TypeToken<LzyResponse<LoginBean>>() {
        }.getType();
        Map<String, Object> map = new HashMap<>();
        map.put("u", username);
        map.put("p", password);
        requestToJson(context, type, UrlManage.API_LOGIN, map, true, callBack);
    }
}
