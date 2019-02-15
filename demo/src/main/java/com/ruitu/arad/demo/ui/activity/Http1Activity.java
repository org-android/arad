package com.ruitu.arad.demo.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hjq.permissions.Permission;
import com.ruitu.arad.base.BaseContentActivity;
import com.ruitu.arad.demo.R;
import com.ruitu.arad.demo.mvp.model.Http1Model;
import com.ruitu.arad.demo.mvp.presenter.Http1Presenter;
import com.ruitu.arad.support.widget.progress.ProgressLayout;
import com.ruitu.arad.util.AppUtils;
import com.ruitu.arad.util.ToastUtils;
import com.ruitu.arad.util.UIUtils;
import com.ruitu.router_module.bean.Version;
import com.ruitu.router_module.util.MapNavUtil;
import com.ruitu.router_module.util.PermissionUtil;

import java.util.HashMap;
import java.util.Map;

public class Http1Activity extends BaseContentActivity<Http1Presenter, Http1Model> {
    private TextView tv_nav, tv_location, tv_ver_info;//导航,定位权限,版本信息
    private TextView tv_activity, tv_fragment;//activity列表测试页面,fragment测试页面

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_http1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_nav = findViewById(R.id.tv_nav);
        tv_location = findViewById(R.id.tv_location);
        tv_ver_info = findViewById(R.id.tv_ver_info);
        tv_activity = findViewById(R.id.tv_activity);
        tv_fragment = findViewById(R.id.tv_fragment);

        tv_nav.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        tv_activity.setOnClickListener(this);
        tv_fragment.setOnClickListener(this);

        rxList.add(p.reqVersion());//请求版本信息
        rxList.add(p.testDispose());//测试停止请求
        showProgress();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == tv_nav) {//导航
            startaNav();
        }
        if (v == tv_location) {//定位权限
            getLocPermission();
        }
        if (v == tv_activity) {
            startActivity(UserListActivity.class);
        }
        if (v == tv_fragment) {

        }
    }

    //开始导航
    private void startaNav() {
        if (AppUtils.isInstallApp(MapNavUtil.BAIDUMAP_PACKAGENAME)) {//已安装百度地图
            Map map = new HashMap();
            map.put(MapNavUtil.BD_LNG, 118.309952);//118.309952,35.073284
            map.put(MapNavUtil.BD_LAT, 35.073284);
            map.put(MapNavUtil.DESTINATION, "我是目标地点");//某些地图需要输入目的地名称
            MapNavUtil.invokeBaiDuMap(this, map);
        } else {
            UIUtils.showAlertDialog(getSupportFragmentManager(), "温馨提示", "该功能需要安装百度地图，是否前往应用市场下载？",
                    "去下载", "取消"
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri uri = Uri.parse("market://details?id=" + MapNavUtil.BAIDUMAP_PACKAGENAME);
                            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            try {
                                startActivity(goToMarket);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }, null);
        }
    }

    private void getLocPermission() {
        if (PermissionUtil.isHasPer(this, Permission.Group.LOCATION)) {
            ToastUtils.showShort("已经拥有定位权限");
        } else {
            PermissionUtil.handleMultiPermission(this, "定位", new PermissionUtil.PermissionListener() {
                @Override
                public void onPermissionOk(boolean isOk) {
                    if (isOk) {
                        ToastUtils.showShortSafe("允许了权限");
                    } else {
                        ToastUtils.showShortSafe("没有权限");
                    }
                }

                @Override
                public void onOpenPermissionPage(boolean isOpen) {
                    if (isOpen) {
                        ToastUtils.showShortSafe("用户打开授权页面");
                    } else {
                        ToastUtils.showShortSafe("用户没有打开授权页面");
                    }
                }
            }, Permission.Group.LOCATION);
        }
    }

    @Override
    public void onReqComplete(int code, boolean isOk, Object data) {
        if (code == 1) {//请求版本信息成功
            Version version = (Version) data;
            tv_ver_info.setText(version.toString());
        }
    }

    @Override
    protected ProgressLayout findProgressLayout() {
        return null;
    }
}
