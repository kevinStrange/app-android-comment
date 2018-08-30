package com.comment.tek.net;

import com.comment.tek.util.APPInfoUtils;
import com.comment.tek.util.Lg;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.base.Request;
import com.lzy.okrx2.adapter.ObservableBody;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * 这里封装多一层，作为中转
 * Created by huanghongfa on 2018/8/23.
 */

public class BaseNetRequestTransfer extends BaseNetRequest {


    public BaseNetRequestTransfer() {

    }


    public <T> Observable<T> reQuest(HttpMethod method, String url, Type type) {

        return reQuest(method, url, type, null);
    }

    public <T> Observable<T> reQuest(HttpMethod method, String url, Type type, HttpParams params) {
        return reQuest(method, url, type, params, null);
    }

    public <T> Observable<T> reQuest(HttpMethod method, String url, Type type, HttpParams params, HttpHeaders headers) {
        return reQuest(method, url, type, null, params, headers);
    }

    public <T> Observable<T> reQuest(HttpMethod method, String url, Class<T> clazz) {
        return reQuest(method, url, clazz, null);
    }

    public <T> Observable<T> reQuest(HttpMethod method, String url, Class<T> clazz, HttpParams params) {
        return reQuest(method, url, clazz, params, null);
    }

    public <T> Observable<T> reQuest(HttpMethod method, String url, Class<T> clazz, HttpParams params, HttpHeaders headers) {
        return reQuest(method, url, null, clazz, params, headers);
    }

    /**
     *
     */
    @Override
    public <T> Observable<T> reQuest(HttpMethod method, String url, Type type, Class<T> clazz, HttpParams params, HttpHeaders headers) {
        Request<T, ? extends Request> request;
        if (method == HttpMethod.GET) request = OkGo.get(url);
        else if (method == HttpMethod.POST) request = OkGo.post(url);
        else if (method == HttpMethod.PUT) request = OkGo.put(url);
        else if (method == HttpMethod.DELETE) request = OkGo.delete(url);
        else if (method == HttpMethod.HEAD) request = OkGo.head(url);
        else if (method == HttpMethod.PATCH) request = OkGo.patch(url);
        else if (method == HttpMethod.OPTIONS) request = OkGo.options(url);
        else if (method == HttpMethod.TRACE) request = OkGo.trace(url);
        else request = OkGo.get(url);
        request.headers(getHeadConfig());
        request.params(params);
        if (type != null) {
            request.converter(new JsonConvert<T>(type));
        } else if (clazz != null) {
            request.converter(new JsonConvert<T>(clazz));
        } else {
            request.converter(new JsonConvert<T>());
        }
        return request.adapt(new ObservableBody<T>());
    }

    private HttpHeaders getHeadConfig() {
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.put("VersionName", APPInfoUtils.getVersionName());
            headers.put("VersionCode", String.valueOf(APPInfoUtils.getVersionCode()));
            headers.put("Channel", APPInfoUtils.getChannel());
//            headers.put("DeviceId", getDeviceId());
            headers.put("SilverBeeVersionName", APPInfoUtils.getVersionName());
        } catch (Exception e) {
            Lg.w(e);
        }
        return headers;
    }


    @Override
    public String getTag() {
        return null;
    }

    @Override
    public String urlAppendToken(String url) {
        return url;
    }
}
