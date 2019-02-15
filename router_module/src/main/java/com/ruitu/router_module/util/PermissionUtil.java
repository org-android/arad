package com.ruitu.router_module.util;

import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.ruitu.arad.util.UIUtils;

import java.util.List;

//基于 XXPermissions 权限申请框架封装的工具类
public class PermissionUtil {

    //处理单一权限申请
    public static void handleSinglePermission(final FragmentActivity activity, final String per
            , final String perStr, final PermissionListener listener) {
        XXPermissions.with(activity)
                .permission(per)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (null != listener) {
                            listener.onPermissionOk(true);
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {//用户永久拒绝权限
                            UIUtils.showAlertDialog(activity.getSupportFragmentManager(), "溫馨提示"
                                    , "程序需要" + perStr + "权限，您拒绝了该权限，请点击\"去设置\"允许\"该程序\"使用" + perStr + "权限！",
                                    "去设置", "取消"
                                    , new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //打开权限设置页面(适配大部分国产手机)
                                            XXPermissions.gotoPermissionSettings(activity);
                                            if (null != listener) {
                                                listener.onOpenPermissionPage(true);
                                            }
                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (null != listener) {
                                                listener.onOpenPermissionPage(false);
                                            }
                                        }
                                    });
                        } else {//用户拒绝部分权限
                            if (null != listener) {
                                listener.onPermissionOk(false);
                            }
                        }
                    }
                });
    }

    //处理多个权限 权限列表写在最后面,可传多个权限
    public static void handleMultiPermission(final FragmentActivity activity, final String perStr
            , final PermissionListener listener, final String... per) {
        if (XXPermissions.isHasPermission(activity, per)) {
            if (null != listener) {
                listener.onPermissionOk(true);
            }
        } else {//没有权限
            XXPermissions.with(activity)
                    .permission(per)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (isAll) {//申请的全部权限都已经授权
                                if (null != listener) {
                                    listener.onPermissionOk(true);
                                }
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {//用户永久拒绝权限
                                UIUtils.showAlertDialog(activity.getSupportFragmentManager(), "溫馨提示"
                                        , "程序需要" + perStr + "权限，您拒绝了该权限，请点击\"去设置\"允许\"该程序\"使用" + perStr + "权限!",
                                        "去设置", "取消"
                                        , new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //打开权限设置页面(适配大部分国产手机)
                                                XXPermissions.gotoPermissionSettings(activity);
                                                if (null != listener) {
                                                    listener.onOpenPermissionPage(true);
                                                }
                                            }
                                        }, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (null != listener) {
                                                    listener.onOpenPermissionPage(false);
                                                }
                                            }
                                        });
                            } else {//用户拒绝部分权限
                                if (null != listener) {
                                    listener.onPermissionOk(false);
                                }
                            }
                        }
                    });
        }
    }

    //检查是否有权限
    public static boolean isHasPer(FragmentActivity activity, String... perStr) {
        return XXPermissions.isHasPermission(activity, perStr);
    }

    //实现了所有回调方法
    public class PermissionListenerImpl implements PermissionListener {

        @Override
        public void onPermissionOk(boolean isOk) {
        }

        @Override
        public void onOpenPermissionPage(boolean isOpen) {
        }
    }

    //权限监听器
    public interface PermissionListener {
//        void onPermissionOk();//用户允许了所有权限
//
//        void onPermisonBad();//用户拒绝了部分(或所有)权限,不是永久禁止,下一次还会提示用户授权权限
//
//        void onDenyOpenPermissionPage();//用户拒绝打开权限设置页面
//
//        void onOpenPermissionPage();//用户打开了系统的权限设置页面

        void onPermissionOk(boolean isOk);//用户是否允许了所有权限

        void onOpenPermissionPage(boolean isOpen);//用户是否打开权限设置页面
    }
}
