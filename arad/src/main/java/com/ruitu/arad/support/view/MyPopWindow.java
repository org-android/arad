package com.ruitu.arad.support.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Lotte_bin on 2018.07.15.
 * 修复showAsDropDown在7.0及以上手机显示位置错乱问题
 */
public class MyPopWindow extends PopupWindow {

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    public MyPopWindow(Context context) {
        super(context);
    }

    public MyPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyPopWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyPopWindow() {
    }

    public MyPopWindow(View contentView) {
        super(contentView);
    }

    public MyPopWindow(int width, int height) {
        super(width, height);
    }

    public MyPopWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public MyPopWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }
}
