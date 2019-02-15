package com.ruitu.arad.base;

import android.text.TextUtils;
import android.view.View;

import com.ruitu.arad.R;
import com.ruitu.arad.support.widget.progress.ProgressLayout;

import java.util.List;

public abstract class BaseContentActivity<P extends BasePresenter, M extends BaseModel>
        extends BaseActivity<P, M> {

    protected ProgressLayout progressLayout;

    protected abstract ProgressLayout findProgressLayout();//加载空白(加载,错误)页面控件

    @Override
    protected void initViews() {
        super.initViews();
        progressLayout = findProgressLayout();
    }

    protected void showLoadingView(List<Integer> skipIds) {//显示加载中...
        if (null != progressLayout) {
            if (isDataEmpty(skipIds)) {
                progressLayout.showLoading();
            } else {
                progressLayout.showLoading(skipIds);
            }
        }
    }

    protected void showContent(List<Integer> skipIds) {//显示页面正常的布局
        if (null != progressLayout) {
            if (isDataEmpty(skipIds)) {
                progressLayout.showContent();
            } else {
                progressLayout.showContent(skipIds);
            }
        }
    }

    protected void showEmptyView(List<Integer> skipIds) {//显示空数据页面
        if (null != progressLayout) {
            showEmptyView(0, getString(R.string.default_empty_title), getString(R.string.default_empty_detail), skipIds);
        }
    }

    protected void showEmptyView(int icon, String title, String description, List<Integer> skipIds) {
        if (icon == 0) {//没有传图标的资源id
            icon = R.mipmap.no_content;//默认空白图标
        }
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.default_empty_title);
        }
        if (TextUtils.isEmpty(description)) {
            title = getString(R.string.default_empty_detail);
        }

        if (isDataEmpty(skipIds)) {
            progressLayout.showEmpty(icon, title, description);
        } else {
            progressLayout.showEmpty(icon, title, description, skipIds);
        }
    }

    protected void showError(View.OnClickListener onClickListener) {//显示错误页面
        if (null != progressLayout) {
            showError(0, getString(R.string.default_error_title), getString(R.string.default_error_detail)
                    , getString(R.string.default_error_btn_txt), onClickListener);
        }
    }

    protected void showError(int icon, String title, String description, String btnTxt, View.OnClickListener onClickListener) {//显示错误页面
        if (icon == 0) {
            icon = R.mipmap.no_content;//默认错误图标
        }
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.default_error_title);
        }
        if (TextUtils.isEmpty(description)) {
            title = getString(R.string.default_error_detail);
        }
        if (TextUtils.isEmpty(btnTxt)) {
            title = getString(R.string.default_error_btn_txt);
        }

        progressLayout.showError(icon, title, description, btnTxt, onClickListener);
    }
}
