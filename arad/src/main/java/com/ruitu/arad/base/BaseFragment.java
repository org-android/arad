package com.ruitu.arad.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ruitu.arad.support.widget.dialog.ProgressHUD;
import com.ruitu.arad.util.TUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment
        implements View.OnClickListener, BaseView {

    public P p;
    private M m;
    protected List<Disposable> rxList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initMvp();
    }

    private void initMvp() {
        p = TUtil.getT(this, 0);
        m = TUtil.getT(this, 1);
        if (null != p && null != m) {
            p.setMV(m, this);
            p.activity = this.getActivity();
        }
    }

    protected void setOnClickListener(View... views) {
        for (View v : views) {
            if (null == v) throw new NullPointerException("不能传入空的控件!");
            v.setOnClickListener(this);
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

    @Override
    public void onDestroy() {
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
                mProgressHUD = ProgressHUD.show(getActivity(), msg, null);
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
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    // 含有Bundle通过Class跳转界面
    public void startActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onReqComplete(int code, boolean isOk, Object data) {

    }
}
