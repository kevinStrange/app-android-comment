package com.comment.tek.interfaces;

import android.app.Dialog;

/**
 * 自定义Dialog按钮点击事件回调
 * Created by huanghongfa on 2018/8/23.
 */

public interface IMenuListener {

    void onMenuResult(int menuType, Dialog dialog, Object value);
}
