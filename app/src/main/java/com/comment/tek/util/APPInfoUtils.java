package com.comment.tek.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by huanghongfa on 2017/6/23.
 */

public class APPInfoUtils {

    private static PackageInfo packageInfo;
    private static ApplicationInfo appInfo;


    /**
     * 初始化APP相关信息
     *
     * @param context
     */
    public void initAppInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            appInfo = packageManager.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (Exception e) {
            Lg.e(" initAppInfo Exception %s", e);
        }
    }

    /**
     * 获取版本名 (例如：1.0.6)
     *
     * @return
     */
    public static String getVersionName() {
        return packageInfo.versionName;
    }

    /**
     * 获取版本的code （例如 ： 10）
     *
     * @return
     */
    public static int getVersionCode() {
        return packageInfo.versionCode;
    }

    /**
     * 获取渠道名 （例如：comment)
     *
     *
     * @return
     */
    public static String getChannel() {
        return "";//appInfo.metaData.getString("InstallChannel");
    }


}
