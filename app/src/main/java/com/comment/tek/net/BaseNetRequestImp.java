package com.comment.tek.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.comment.tek.bean.LzyResponse;
import com.comment.tek.dialog.LoadingDialog;
import com.comment.tek.interfaces.IRequestListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.BitmapConvert;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableBody;
import com.lzy.okrx2.adapter.ObservableResponse;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 这里实现网络请求
 * Created by huanghongfa on 2018/8/23.
 */

public class BaseNetRequestImp extends BaseNetRequestTransfer {


    private static BaseNetRequestImp api;

    private static BaseNetRequestImp getInstance() {
        if (null == api) {
            api = new BaseNetRequestImp();
        }
        return api;
    }


    private static Observable<String> getString(String param) {
        HttpParams params = new HttpParams();
        params.put("bbb", param);
        return getInstance().reQuest(HttpMethod.GET, "", String.class, params);
    }

    /**
     * @param header
     * @param param
     * @return
     */
    public static Observable<Response<Bitmap>> getBitmap(String header, String param) {
        return OkGo.<Bitmap>post("Urls.URL_IMAGE")//
                .headers("aaa", header)//
                .params("bbb", param)//
                .converter(new BitmapConvert())//
                .adapt(new ObservableResponse<Bitmap>());
    }

    public static Observable<Response<File>> getFile(String header, String param) {
        return OkGo.<File>get("Urls.URL_DOWNLOAD")//
                .headers("aaa", header)//
                .params("bbb", param)//
                .converter(new FileConvert())//
                .adapt(new ObservableResponse<File>());
    }


    private static <T> Observable<T> getData(HttpMethod method, Type type, String url, HttpParams params) {
        return getInstance().reQuest(method, url, type, params);
    }

    private static <T> Observable<T> getData(HttpMethod method, Type type, String url) {
        return getInstance().reQuest(method, url, type);
    }

    /**
     * 基础的无嵌套网络请求可以走这个方法 cls 为LzyResponse<T> 中data 的类型
     *
     * @param context
     * @param type
     * @param url
     * @param showDialog
     * @param param
     * @param callBack
     * @param <T>
     */
    public static <T> void request(final Context context, HttpMethod method, Type type, String url, final boolean showDialog, HttpParams param, final IRequestListener<T> callBack) {
        getData(method, type, url, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog(context, showDialog, "请稍后");
                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        if (showDialog && context != null && context instanceof BaseActivity) {
//                            ((BaseActivity) context).addDisposable(d);
//                        }
                    }

                    @Override
                    public void onNext(Object o) {
                        if (o instanceof LzyResponse) {
                            callBack.onSuccess((T) ((LzyResponse) o).data);
                        } else {
                            callBack.onSuccess((T) o);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(e);
                        dismissDialog(showDialog);
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog(showDialog);
                    }
                });

    }



    public static <T> void requestToJson(final Context context,
                                         Type type,
                                         String url, Map<String, Object> map,
                                         final boolean showDialog,
                                         final IRequestListener<T> callBack) {
        OkGo.<T>post(url)
                .converter(new JsonConvert<T>(type))
//                .tag(entity.getTag())
//                .headers("Token", entity.getToken())
//                .headers("Secret", entity.getSecretHead().trim())
                .upJson(new JSONObject(map))
                .adapt(new ObservableBody<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog(context, showDialog, "请稍后");

                    }
                })
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        if (showDialog && context != null && context instanceof BaseActivity) {
//                            ((BaseActivity) context).addDisposable(d);
//                        }
                    }

                    @Override
                    public void onNext(Object o) {
                        if (o instanceof LzyResponse) {
                            callBack.onSuccess((T) ((LzyResponse) o).data);
                        } else {
                            callBack.onSuccess((T) o);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(e);
                        dismissDialog(showDialog);
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog(showDialog);
                    }
                });

    }

    /**
     * 显示请求提示框
     *
     * @return
     */

    private static void showDialog(Context context, boolean isShowDialog, String msg) {
        if (isShowDialog) {
            LoadingDialog.showLoading(context, msg);
        }
    }

    /**
     * 隐藏请求提示框
     *
     * @return
     */

    private static void dismissDialog(boolean isShowDialog) {
        if (isShowDialog) {
            LoadingDialog.dismissLoading();
        }
    }
}
