package com.ruitu.player18;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hjq.permissions.Permission;
import com.ruitu.arad.base.BaseActivity;
import com.ruitu.arad.util.ToastUtils;
import com.ruitu.arad.util.UIUtils;
import com.ruitu.player18.player.VideoViewActivity;
import com.ruitu.player18.radio.FmOnlineActivity;
import com.ruitu.player18.tv.TvActivity;
import com.ruitu.router_module.util.AlertUtil;
import com.ruitu.router_module.util.PermissionUtil;

public class MainActivity extends BaseActivity {
    private LinearLayout ll_1, ll_2, ll_3, ll_4, ll_5, ll_6;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ll_1 = findViewById(R.id.ll_1);
        ll_2 = findViewById(R.id.ll_2);
        ll_3 = findViewById(R.id.ll_3);
        ll_4 = findViewById(R.id.ll_4);
        ll_5 = findViewById(R.id.ll_5);
        ll_6 = findViewById(R.id.ll_6);

        alertDialog = new AlertDialog.Builder(this, R.style.Dialog_FS).create();
        setHeadTitle(getResources().getString(R.string.app_name));
        setNoNavigationIcon();
        setListeners();

        PermissionUtil.handleMultiPermission(this, "存储", new PermissionUtil.PermissionListener() {
            @Override
            public void onPermissionOk(boolean isOk) {

            }

            @Override
            public void onOpenPermissionPage(boolean isOpen) {

            }
        }, Permission.Group.STORAGE);
    }

    private void setListeners() {
        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);
        ll_4.setOnClickListener(this);
        ll_5.setOnClickListener(this);
        ll_6.setOnClickListener(this);
    }

    private View alert_view;//对话框的布局

    public void showDialog() {
        alert_view = View.inflate(this, R.layout.alert_video_menu, null);

        alert_view.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alert_view.findViewById(R.id.tv_video_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(VideoViewActivity.class);
            }
        });
        alert_view.findViewById(R.id.tv_vitamio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                ToastUtils.showShortSafe("暂不支持");
//                startActivity(PlayerActivity.class);
            }
        });

        alertDialog.show();
        AlertUtil.setAlertCenterStyle(alertDialog.getWindow(), alert_view, this);

        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        alertDialog.setCanceledOnTouchOutside(false);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == ll_1) {
            showDialog();
        }
        if (v == ll_2) {
            startActivity(TvActivity.class);
        }
        if (v == ll_3) {//在线fm
            startActivity(FmOnlineActivity.class);
        }
        if (v == ll_4) {
            ToastUtils.showShortSafe("4");
        }
        if (v == ll_5) {
            ToastUtils.showShortSafe("5");
        }
        if (v == ll_6) {
            String txt = "名称：18视频\n"
                    + "版本：1.0.0\n"
                    + "版权：爱谁谁";
            UIUtils.showAlertDialog(getSupportFragmentManager(), "记好了", txt
                    , "", "OK"
                    , null, null);
        }
    }
}
