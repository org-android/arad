package com.ruitu.arad.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ruitu.arad.Arad;
import com.ruitu.arad.support.widget.dialog.AlertDialogFragment;
import com.ruitu.arad.support.widget.dialog.CommentDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用UI
 * Created by beanu on 14-8-22.
 */
public class UIUtils {

    /**
     * 获取上下文
     */
    public static Context getContext() {
        return Arad.app;
    }

    /**
     * 获取布局
     *
     * @param resId
     * @return
     */
    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }


    public static int convertDpToPixel(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    /**
     * AlertDialog
     */
    public static AlertDialogFragment showAlertDialog(FragmentManager fm, String title
            , String message, String positiveButtonText
            , String negativeButtonText, DialogInterface.OnClickListener positiveListener
            , DialogInterface.OnClickListener negativeListener) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        AlertDialogFragment dialog = AlertDialogFragment.newInstance(title,
                message, positiveButtonText, negativeButtonText);
        dialog.setNegativeListener(negativeListener);
        dialog.setPositiveListener(positiveListener);
        dialog.show(fm, "dialog");
        return dialog;
    }

    /**
     * 评论提交
     */
    public static void showCommentDialog(FragmentManager fm, String title,
                                         String positiveButtonText, String negativeButtonText,
                                         CommentDialogFragment.PositiveClick onclick) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        CommentDialogFragment dialog = CommentDialogFragment.newInstance(title,
                positiveButtonText, negativeButtonText);
        dialog.setPositiveClick(onclick);
        dialog.show(fm, "dialog");

    }


    /**
     * 隐藏一个等待dialog fm=getSupportFragmentManager()
     */
    public static void hideDialog(FragmentManager fm) {
        DialogFragment prev = (DialogFragment) fm.findFragmentByTag("dialog");
        if (prev != null) {
            prev.dismiss();
        }
    }

    /**
     * 打开网页
     */
    public static void openWeb(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 打电话拨号
     */
    public static void dial(Context ctx, String telephone) {
        try {
            if (telephone != null && !telephone.equals("")) {
                Uri uri = Uri.parse("tel:" + telephone);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                ctx.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 分享
     *
     * @param context
     * @param title
     * @param content
     */
    public static void showShareext(Context context, String title, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "请选择"));
    }


    /**
     * 分享
     *
     * @param context
     * @param title
     * @param content
     */
    public static void showShare(Context context, String title, String content) {

        String weibo = "com.sina.weibo";
        String qq = "com.tencent.mobileqq";
        String qqweibo = "com.tencent.WBlog";
        String weixin = "com.tencent.mm";
        String renren = "com.renren.xiaonei.android";
        String google = "com.google.android.apps.plus";

        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text/plain");
        List<ResolveInfo> resInfo = context.getPackageManager()
                .queryIntentActivities(it, 0);
        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (ResolveInfo info : resInfo) {

                String pkgName = info.activityInfo.applicationInfo.packageName;
                if (weibo.equals(pkgName) || qq.equals(pkgName)
                        || qqweibo.equals(pkgName) || weixin.equals(pkgName)
                        || renren.equals(pkgName) || google.equals(pkgName)) {
                    Intent targeted = new Intent(Intent.ACTION_SEND);
                    targeted.setType("text/plain");
                    targeted.putExtra(Intent.EXTRA_TITLE, title);
                    ActivityInfo activityInfo = info.activityInfo;
                    targeted.putExtra(Intent.EXTRA_TEXT, content);
                    targeted.setPackage(activityInfo.packageName);
                    targetedShareIntents.add(targeted);
                }
            }
            if (targetedShareIntents.size() > 0) {
                Intent chooserIntent = Intent.createChooser(
                        targetedShareIntents.remove(0), "Select app to share");
                if (chooserIntent == null) {
                    return;
                }
                // A Parcelable[] of Intent or LabeledIntent objects as set with
                // putExtra(String, Parcelable[]) of additional activities to
                // place
                // a the front of the list of choices, when shown to the user
                // with a
                // ACTION_CHOOSER.
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        targetedShareIntents.toArray(new Parcelable[]{}));

                try {
                    context.startActivity(chooserIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context,
                            "Can't find share component to share",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Can't find share component to share",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 设置删除线
     */
    public static void setDelLine(TextView tv) {
        if (null != tv) {
            tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    /**
     * 设置下划线
     */
    public static void setUnderLine(TextView tv) {
        if (null != tv) {
            tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    public static boolean isEtEmpty(EditText et) {
        try {
            if (null == et) return true;
            String txt = et.getText().toString().trim();
            return TextUtils.isEmpty(txt);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * View是否可见
     *
     * @param scrollView
     * @param childView
     * @return
     */
    public static boolean isViewVisible(RecyclerView scrollView, View childView) {
        Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);
        if (childView.getLocalVisibleRect(scrollBounds)) {
            //子控件至少有一个像素在可视范围内
            // Any portion of the childView, even a single pixel, is within the visible window
            return true;
        } else {
            //子控件完全不在可视范围内
            // NONE of the childView is within the visible window
            return false;
        }
    }

    //测试和上面的方法效果是一样的
    public static boolean checkIsVisible(View view) {
        // 如果已经加载了，判断广告view是否显示出来，然后曝光
        int screenWidth = ScreenUtils.getScreenWidth();
        int screenHeight = ScreenUtils.getScreenHeight();
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        view.getLocationInWindow(location);
        if (view.getLocalVisibleRect(rect)) {
            return true;
        } else {
            //view已不在屏幕可见区域;
            return false;
        }
    }

    public static void hideInput(Context context, IBinder token) {
//        InputMethodManager inputMethodManager
//                = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        try {
            InputMethodManager inputMethodManager
                    = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(token, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
