package com.ruitu.router_module;

import android.app.Activity;
import android.text.TextUtils;

import com.ruitu.arad.util.ToastUtils;

public class CommonUtils {

    public static boolean isLogin() {
        if (AppHolder.instance() == null || AppHolder.instance().getUser() == null ||
                TextUtils.isEmpty(AppHolder.instance().getUser().getId())) {
            return false;
        }
        return true;
    }

    public static boolean isLoginToActivity(Activity activity) {
        if (null == activity) {
            return false;
        }
        if (!isLogin()) {
            try {
//                A_Rutils.startActivity(activity, "com.ruitu.mall_98_2.mine.LoginActivity");
                ToastUtils.showShortSafe("跳转到登录页面...请填写代码");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
