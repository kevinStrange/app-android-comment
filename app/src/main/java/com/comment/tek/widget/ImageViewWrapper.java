package com.comment.tek.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * ImageView 包装器<br/>
 *
 * @author ChenRen / 2016-08-30
 */
public class ImageViewWrapper extends SimpleDraweeView {

    public ImageViewWrapper(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ImageViewWrapper(Context context) {
        super(context);
    }

    public ImageViewWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ImageViewWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
