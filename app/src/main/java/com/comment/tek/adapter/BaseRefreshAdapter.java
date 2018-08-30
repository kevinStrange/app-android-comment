package com.comment.tek.adapter;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.comment.tek.activity.R;
import com.comment.tek.interfaces.OnItemClickListener;
import com.comment.tek.interfaces.OnLoadMoreCallBack;
import com.comment.tek.view.BaseRefreshViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanghongfa on 2018/8/30.
 */

public abstract class BaseRefreshAdapter<T> extends RecyclerView.Adapter<BaseRefreshViewHolder> {
    public static final int TYPE_FOOTER_VIEW = 100002;
    public static final int TYPE_COMMON_VIEW = 100001;
    private OnItemClickListener<T> mItemClickListener;
    private OnLoadMoreCallBack mLoadMoreCallBack;
    protected Context mContext;
    protected List<T> mDatas;

    protected abstract void convert(BaseRefreshViewHolder holder, T data, int position);

    protected abstract int getItemLayoutId();


    private boolean mOpenLoadMore;
    private STATUS mCurrentStatus = STATUS.NORMAL;

    public enum STATUS {
        NORMAL,
        LOADING,
        LOAD_SUCCESS,
        LOAD_FAIL,
        NO_MORE
    }

    public BaseRefreshAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        mContext = context;
        mOpenLoadMore = isOpenLoadMore;
        mDatas = datas == null ? new ArrayList<T>() : datas;
    }

    @Override
    public BaseRefreshViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseRefreshViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_COMMON_VIEW:
                viewHolder = BaseRefreshViewHolder.create(mContext, getItemLayoutId(), parent);
                break;
            case TYPE_FOOTER_VIEW:
//                viewHolder = BaseRefreshViewHolder.create(mContext, R.layout.layout_foot_view, parent);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseRefreshViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_COMMON_VIEW:
                bindCommonItem(holder, position);
                break;
            case TYPE_FOOTER_VIEW:
                bindFoodView(holder);
                break;
        }
    }


    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position) {
        final BaseRefreshViewHolder viewHolder = (BaseRefreshViewHolder) holder;
        convert(viewHolder, mDatas.get(position), position);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null)
                    mItemClickListener.onItemClick(viewHolder, mDatas.get(position), position);
            }
        });
    }

    protected void bindFoodView(BaseRefreshViewHolder holder) {
        switch (mCurrentStatus) {
            case NORMAL:
                setFootText(holder, R.string.click_see_more);
                setProgressBarIsVisible(holder, View.GONE);
                break;
            case LOADING:
                setFootText(holder, R.string.loading);
                setProgressBarIsVisible(holder, View.VISIBLE);
                break;
            case LOAD_SUCCESS:
                setFootText(holder, R.string.load_success);
                setProgressBarIsVisible(holder, View.GONE);
                break;
            case LOAD_FAIL:
                setFootText(holder, R.string.load_fail);
                setProgressBarIsVisible(holder, View.GONE);
                break;
            case NO_MORE:
                setFootText(holder, R.string.no_data_more_hint);
                setProgressBarIsVisible(holder, View.GONE);
                break;
        }
        holder.getConvertView().setClickable(mCurrentStatus == STATUS.NORMAL);
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentStatus != STATUS.NORMAL) {
                    return;
                }
                if (mLoadMoreCallBack != null) {
                    mLoadMoreCallBack.onLoadMore();
                }
                setStatus(STATUS.LOADING);
            }
        });
    }

    private boolean isFooterView(int position) {
        return mOpenLoadMore && position >= getItemCount() - 1;
    }

    @Override
    public int getItemCount() {
        return mOpenLoadMore&&!mDatas.isEmpty() ? mDatas.size() + 1 : mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW;
        } else {
            return TYPE_COMMON_VIEW;
        }
    }

    public T getItem(int position) {
        if (mDatas.isEmpty()) {
            return null;
        }
        return mDatas.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnLoadMoreCallBack(OnLoadMoreCallBack loadMoreCallBack) {
        mLoadMoreCallBack = loadMoreCallBack;
    }

    private void setStatus(STATUS status) {
        if (mCurrentStatus == status) {
            return;
        }
        switch (status) {
            case NORMAL:
            case LOADING:
            case NO_MORE:
                mCurrentStatus = status;
                break;
            case LOAD_SUCCESS:
            case LOAD_FAIL:
                mCurrentStatus = status;
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setStatus(STATUS.NORMAL);
                    }
                }, 500);
                break;
        }
        notifyItemChanged(getItemCount() - 1);
    }

    /**
     * 加载完成是否
     *
     * @param success
     */
    public void LoadFinish(boolean success) {
        if (success) {
            setStatus(STATUS.LOAD_SUCCESS);
        } else {
            setStatus(STATUS.LOAD_FAIL);
        }
    }

    /**
     * 加载完成是否
     *
     * @param hasMore
     */
    public void setHasMore(boolean hasMore) {
        if (hasMore) {
            setStatus(STATUS.NORMAL);
        } else {
            setStatus(STATUS.NO_MORE);
        }
    }


    private void setFootText(BaseRefreshViewHolder holder, @StringRes int resId) {
//        TextView tv = (TextView) holder.getView(R.id.tv_foot);
//        tv.setText(resId);
    }

    private void setProgressBarIsVisible(BaseRefreshViewHolder holder, int visible) {
//        View view = holder.getView(R.id.progress_bar);
//        view.setVisibility(visible);
    }

    public void setData(List<T> data) {
        mDatas = data == null ? new ArrayList<T>() : data;
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        if (data != null && !data.isEmpty()) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

}
