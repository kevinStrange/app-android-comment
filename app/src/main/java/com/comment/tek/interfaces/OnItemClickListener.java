package com.comment.tek.interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * Created by huanghongfa on 2018/8/30.
 */

public interface OnItemClickListener<T> {
    void onItemClick(RecyclerView.ViewHolder holder, T data, int position);
}
