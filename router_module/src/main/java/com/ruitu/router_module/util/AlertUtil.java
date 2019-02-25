package com.ruitu.router_module.util;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ruitu.router_module.R;

public class AlertUtil {
    public static void setAlertBottomInStyle(Window window, View alert_view, Activity activity) {
        window.setContentView(alert_view);
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.DialogBottomStyle);//添加动画

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = activity.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(layoutParams);
    }

    public static void setAlertCenterStyle(Window window, View alert_view, Activity activity) {
        window.setContentView(alert_view);
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
//        window.setWindowAnimations(R.style.DialogBottomStyle);//添加动画

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = activity.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(layoutParams);
    }
}
