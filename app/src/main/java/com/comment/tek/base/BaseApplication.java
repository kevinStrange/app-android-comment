package com.comment.tek.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.comment.tek.util.APPInfoUtils;
import com.comment.tek.util.Lg;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

import static com.lzy.okgo.OkGo.DEFAULT_MILLISECONDS;

/**
 * Created by huanghongfa on 2018/8/23.
 */

public class BaseApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        APPInfoUtils appInfoUtils = new APPInfoUtils();
        appInfoUtils.initAppInfo(mContext);
//        initOKHttpClient();
    }

    public static Context getContext() {
        return mContext;
    }


    public void initOKHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkGo.getInstance().init(this)//必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .addCommonHeaders(getHeadConfig());                      //全局公共头
//                .addCommonParams(params);                       //全局公共参数
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        //非必要情况，不建议使用，第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
//        builder.addInterceptor(new ChuckInterceptor(this));
        //全局的读取超时时间
        builder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
//        try {
//            //必须调用初始化
//            OkGo.init(mInstance);
//            //以下都不是必须的，根据需要自行选择
//            // 添加http头
//            OkGo.getInstance()
//                    .setConnectTimeout(DEFAULT_MILLISECONDS)//全局的连接超时时间
//                    .setReadTimeOut(DEFAULT_MILLISECONDS)  //全局的读取超时时间
//                    .setWriteTimeOut(DEFAULT_MILLISECONDS)//全局的写入超时时间
//                    .addCommonHeaders(getHeadConfig())//添加头
//                    .setRetryCount(0)//超时重连接次数
//                    .setCookieStore(new PersistentCookieStore());//cookie持久化存储，如果cookie不过期，则一直有效;
//            if (BuildConfig.DEBUG)
//                OkGo.getInstance().debug("OkHttpUtils");
//            else
//                OkGo.getInstance().debug("OkHttpUtils", Level.ALL, BuildConfig.DEBUG);
//        } catch (Exception e) {
//            Ln.w(e);
//        }
    }

//    /**
//     * 保存本地崩溃日志
//     */
//    private void initCrashReport() {
//        try {
//            LogReport.getInstance()
//                    .setCacheSize(30 * 1024 * 1024)//支持设置缓存大小，超出后清空
//                    //.setLogDir(getApplicationContext(), "sdcard/" + this.getString(this.getApplicationInfo().labelRes) + "/")//定义路径为：sdcard/[app name]/
//                    // 默认log路径: Android/data/[app_package_name]/log
//                    .setWifiOnly(true)//设置只在Wifi状态下上传，设置为false为Wifi和移动网络都上传
//                    .setLogSaver(new CrashWriter(getApplicationContext()))//支持自定义保存崩溃信息的样式
//                    //.setEncryption(new AESEncode()) //支持日志到AES加密或者DES加密，默认不开启
//                    .init(getApplicationContext());
//            // 现在只保存在本地, 没做日志上传处理
//        } catch (Exception e) {
//        }
//
//    }

    private HttpHeaders getHeadConfig() {
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.put("VersionName", APPInfoUtils.getVersionName());
            headers.put("VersionCode", String.valueOf(APPInfoUtils.getVersionCode()));
            headers.put("Channel", APPInfoUtils.getChannel());
            headers.put("DeviceId", getDeviceId());
            headers.put("SilverBeeVersionName", APPInfoUtils.getVersionName());
        } catch (Exception e) {
            Lg.w(e);
        }
        return headers;
    }

    /**
     * 获取手机的设备ID，为了区分唯一性，采用DEVICE_ID+ANDROID_ID区分
     *
     * @return DeviceId
     */
    public String getDeviceId() {
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String DEVICE_ID = "";//tm.getDeviceId();
            String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
            Lg.d("ANDROID DEVICE_ID " + DEVICE_ID + " ANDROID_ID " + ANDROID_ID);
            return DEVICE_ID + ANDROID_ID;
        } catch (Exception e) {
            Lg.e(e);
        }
        return "11111111111111111111";
    }
}
