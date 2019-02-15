package com.ruitu.arad.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruitu.arad.base.BaseContentActivity;
import com.ruitu.arad.demo.mvp.model.MainModel;
import com.ruitu.arad.demo.mvp.presenter.MainPresenter;
import com.ruitu.arad.demo.ui.activity.Http1Activity;
import com.ruitu.arad.support.widget.progress.ProgressLayout;
import com.ruitu.arad.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseContentActivity<MainPresenter, MainModel> {
    private TextView tv_txt, tv_loading, tv_content, tv_empty, tv_error;//
    private LinearLayout ll_btn;

    private List<Integer> idList = new ArrayList<>();

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected ProgressLayout findProgressLayout() {
        return findViewById(R.id.progress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv_txt = findViewById(R.id.tv_txt);
        tv_loading = findViewById(R.id.tv_loading);
        tv_content = findViewById(R.id.tv_content);
        tv_empty = findViewById(R.id.tv_empty);
        tv_error = findViewById(R.id.tv_error);
        ll_btn = findViewById(R.id.ll_btn);

        tv_loading.setOnClickListener(this);
        tv_content.setOnClickListener(this);
        tv_empty.setOnClickListener(this);
        tv_error.setOnClickListener(this);

        idList.add(R.id.ll_btn);

        setRightText("下一页");
    }

    @Override
    protected void onRightTextClick() {
        startActivity(Http1Activity.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == tv_loading) {
            showLoadingView(idList);
        }
        if (v == tv_content) {
            showContent(idList);
        }
        if (v == tv_empty) {
            showEmptyView(idList);
        }
        if (v == tv_error) {
            showError(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showShortSafe("重新加载");
                    showLoadingView(idList);
                    ll_btn.setVisibility(View.VISIBLE);
                    p.getData1();
                }
            });
        }
    }

    @Override
    public void onReqComplete(int code, boolean isOk, Object data) {
        if (code == 1) {//第一个请求完成
            ToastUtils.showShortSafe("第一个数据请求完成");
            List<String> data1 = (List<String>) data;
            tv_txt.setText("请求完成的第一个数据是:" + data1.get(0));
        }
    }
}
