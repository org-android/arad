package com.ruitu.arad.base.base_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.ruitu.arad.Arad;
import com.ruitu.arad.base.BaseModel;
import com.ruitu.arad.base.BasePresenter;
import com.ruitu.arad.support.widget.progress.ProgressLayout;

public abstract class BaseRefreshFragment<T extends BasePresenter, E extends BaseModel>
        extends BaseContentFragment<T, E> {
    public static final int PAGE_SIZE = 20;//默认的每页加载的数据数量

    protected LRecyclerView lRecyclerView;
    protected LRecyclerViewAdapter lAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lAdapter = new LRecyclerViewAdapter(initAdapter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        initRecyclerView(v);
        return v;
    }

    protected void initRecyclerView(View v) {
        lRecyclerView = findRecyclerView(v);
        progressLayout = findProgressLayout(v);
        if (null != lRecyclerView) {

            if (null != Arad.refreshHeader) {// new CustomRefreshHeader(context)
                lRecyclerView.setRefreshHeader(Arad.refreshHeader);
            }

            lRecyclerView.setAdapter(lAdapter);
            lRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//            setLoadMoreEnabled(false);//默认不开启加载更多功能
            //设置底部加载文字提示
            lRecyclerView.setFooterViewHint("努力加载中", "加载完成", "加载失败,点击重试");

            lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    reqFirst();
                }
            });
            lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    reqMore();
                }
            });
//            lRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
//                @Override
//                public void reload() {
//                    reReq();
//                }
//            });
        }

        if (null != lAdapter) {
            //设置点击事件
            lAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    BaseRefreshFragment.this.onItemClick(v, position);
                }
            });
            lAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public void onItemLongClick(View v, int position) {
                    BaseRefreshFragment.this.onItemLongClick(v, position);
                }
            });
        }
    }

    protected void refreshComplete() {//当前页面数据加载完成
        if (null != lRecyclerView) {
            lRecyclerView.refreshComplete(PAGE_SIZE);
        }
    }

    protected void refreshNoMoreData() {//所有数据都加载完成了
        if (null != lRecyclerView) {
            lRecyclerView.setNoMore(true);
        }
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        refreshComplete();
    }

    @Override
    protected abstract int getLayoutRes();

    @Override
    protected abstract void initViews(View v);

    @Override
    protected abstract ProgressLayout findProgressLayout(View v);

    protected abstract LRecyclerView findRecyclerView(View v);

    protected void onItemClick(View v, int pos) {

    }

    protected void onItemLongClick(View v, int pos) {

    }

    protected abstract RecyclerView.Adapter initAdapter();

    protected abstract void reqFirst();

    protected abstract void reqMore();
}
