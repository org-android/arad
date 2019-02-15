package com.ruitu.arad.support.widget.progress;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2018.07.18.
 * 处理沉浸式,弹出键盘时toolbar被顶出去的bug
 */
public class MySoftInputRelativeLayout extends RelativeLayout {

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            insets.left = 0;
            insets.top = 0;
            insets.right = 0;
        }
        return super.fitSystemWindows(insets);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(0, 0, 0, insets.getSystemWindowInsetBottom()));
        } else {
            return insets;
        }
    }
//    作者：manimaniho
//    链接：https://www.jianshu.com/p/1b22a1d2a7b8

    public MySoftInputRelativeLayout(Context context) {
        super(context);
    }

    public MySoftInputRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySoftInputRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MySoftInputRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
