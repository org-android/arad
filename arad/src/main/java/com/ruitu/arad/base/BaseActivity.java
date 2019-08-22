package com.ruitu.arad.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.ruitu.arad.R;
import com.ruitu.arad.support.widget.dialog.ProgressHUD;
import com.ruitu.arad.util.TUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity
        implements BaseView, View.OnClickListener {
    public P p;
    private M m;
    protected List<Disposable> rxList = new ArrayList<>();

    protected Toolbar toolbar;
    protected TextView tv_title;//标题
    protected TextView tv_right_text;//标题栏右上角的文字

    protected abstract int getLayoutRes();//加载布局文件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        disableSlideBack();
        initMvp();
        initViews();
    }

    protected void initViews() {
        toolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.tv_title);
        tv_right_text = findViewById(R.id.tv_right_text);

        if (null != toolbar) {
            toolbar.setNavigationIcon(R.mipmap.arad_ic_back_normal);
            toolbar.setNavigationOnClickListener(this);
            toolbar.setTitle("");
        }
        if (null != tv_right_text) {
            tv_right_text.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == -1) {//点击的是toolbar左上角的按钮
            onBackPressed();
        }
        if (v == tv_right_text) {//点击右上角的标题
            onRightTextClick();
        }
    }

    protected void onRightTextClick() {
    }

    protected void setHeadTitle(String title) {//设置标题栏的标题
        if (null != tv_title) {
            tv_title.setText(title);
        }
    }

    protected void setRightText(String rightText) {//设置标题栏右上角的文字
        if (null != tv_right_text) {
            tv_right_text.setText(rightText);
        }
    }

    protected String getRightText() {//获取标题栏右上角的文字
        if (null != tv_right_text) {
            return tv_right_text.getText().toString().trim();
        }
        return "";
    }

    protected void setNavigationIcon(int iconId) {
        if (null != toolbar) {
            toolbar.setNavigationIcon(iconId);
        }
    }

    protected void setNoNavigationIcon() {
        if (null != toolbar) {
            toolbar.setNavigationIcon(null);
        }
    }

    public boolean isDataEmpty(List list) {//判断list数据是否为空:null 或者size = 0 视为空
        return null == list || list.size() == 0;
    }

    protected void recycleBitmap(Bitmap bitmap) {//回收bitmap
        if (null != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    private void initMvp() {
        p = TUtil.getT(this, 0);
        m = TUtil.getT(this, 1);
        if (null != p && null != m) {
            p.setMV(m, this);
            p.activity = this;
        }
    }

    protected void setOnClickListener(View... views) {
        for (View v : views) {
            if (null == v) throw new NullPointerException("不能传入空的控件!");
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onReqComplete(int code, boolean isOk, Object data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isDataEmpty(rxList)) {
            int num = rxList.size();
            for (int i = 0; i < num; i++) {
                if (!rxList.get(i).isDisposed()) {
                    rxList.get(i).dispose();//注销所有的请求
                }
            }
        }
    }

    public void enableSlideBack() {
        ParallaxHelper.enableParallaxBack(this);
    }

    public void disableSlideBack() {
        ParallaxHelper.disableParallaxBack(this);
    }

    private static ProgressHUD mProgressHUD;

    // 显示一个等待dialog，内容交互或者提交的时候使用 true显示 false隐藏
    public ProgressHUD showProgress(boolean show) {
        mProgressHUD = showProgressWithText(show, "加载中...");
        return mProgressHUD;
    }

    // 显示一个等待dialog，内容交互或者提交的时候使用 是否要显示 msg 要显示的文字
    public ProgressHUD showProgressWithText(boolean show, String msg) {
        try {
            if (show) {
                mProgressHUD = ProgressHUD.show(this, msg, null);
            } else {
                if (mProgressHUD != null) {
                    mProgressHUD.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mProgressHUD;
    }

    public ProgressHUD showProgress() {
        return showProgress(true);
    }

    public void hideProgress() {
        showProgress(false);
    }

    // 通过Class跳转界面
    public void startActivity(Class cls) {
        startActivity(cls, null);
    }

    // 通过Class跳转界面
    public void startActivityForResult(Class cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    // 含有Bundle通过Class跳转界面
    public void startActivityForResult(Class cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    // 含有Bundle通过Class跳转界面
    public void startActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
