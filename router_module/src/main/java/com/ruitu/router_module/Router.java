package com.ruitu.router_module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

//路由模块处理模块间页面跳转
public class Router {
    public static Fragment getFragment(String name) {
        Fragment fragment;
        try {
            Class fragmentClass = Class.forName(name);
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            return null;
        }
        return fragment;
    }

    public static Object getModuleServ(String name) {
        Object object;
        try {
            Class aClass = Class.forName(name);
            object = aClass.newInstance();
        } catch (Exception e) {
            return null;
        }
        return object;
    }

    public static void startActivity(Context context, String name) throws ClassNotFoundException {
        startActivity(context, name, null);
    }

    /**
     * @param name   全类名
     * @param bundle 传值对象
     * @throws ClassNotFoundException 找不到类,反射失败异常
     */
    public static void startActivity(Context context, String name, Bundle bundle) throws ClassNotFoundException {
        Class clazz = Class.forName(name);
        startActivity(context, clazz, bundle);
    }

    public static void startActivity(Context context, Class clazz, Bundle bundle) {
        context.startActivity(getIntent(context, clazz, bundle));
    }

    private static Intent getIntent(Context context, Class clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        return intent;
    }

    public static void startNewActivity(Context context, String name, Bundle bundle)
            throws ClassNotFoundException {
        Class clazz = Class.forName(name);
        Intent i = new Intent(context, clazz);
        if (null != bundle) {
            i.putExtras(bundle);
        }
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }
}
