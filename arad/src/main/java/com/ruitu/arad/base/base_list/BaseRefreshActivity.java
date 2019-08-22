package com.ruitu.arad.base.base_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.ruitu.arad.Arad;
import com.ruitu.arad.R;
import com.ruitu.arad.base.BaseContentActivity;
import com.ruitu.arad.base.BaseModel;
import com.ruitu.arad.base.BasePresenter;

public abstract class BaseRefreshActivity<T extends BasePresenter, E extends BaseModel>
        extends BaseContentActivity<T, E> {

    public static final int PAGE_SIZE = 20;//默认的每页加载的数据数量

    protected LRecyclerView lRecyclerView;
    protected LRecyclerViewAdapter lAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lRecyclerView = findRecyclerView();
        lAdapter = new LRecyclerViewAdapter(initAdapter());

        initRecyclerView();//按照自己的需求初始化RecyclerView
    }

    protected void initRecyclerView() {
        if (null != lRecyclerView) {

//            CustomRefreshHeader header = new CustomRefreshHeader(this);
//            header.setHintTextColor(R.color.l_rcv_color);
//            header.setViewBackgroundColor(R.color.white);
            if (null != Arad.refreshHeader) {// new CustomRefreshHeader(context)
                lRecyclerView.setRefreshHeader(Arad.refreshHeader);
            }

            lRecyclerView.setAdapter(lAdapter);
            lRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//            setLoadMoreEnabled(false);//默认不开启加载更多功能
            //设置底部加载文字提示
            lRecyclerView.setFooterViewHint("努力加载中", "加载完成", "加载失败,点击重试");
            lRecyclerView.setFooterViewColor(R.color.l_rcv_color, R.color.l_rcv_color, R.color.white);
            lRecyclerView.setHeaderViewColor(R.color.l_rcv_color, R.color.l_rcv_color, R.color.white);

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
//            //设置下拉刷新样式等
//            lRecyclerView.setRefreshProgressStyle(ProgressStyle.BallGridPulse);
//            lRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
//            lRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        }

        if (null != lAdapter) {
            //设置点击事件
            lAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    BaseRefreshActivity.this.onItemClick(v, pos);
                }
            });
            lAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public void onItemLongClick(View v, int pos) {
                    BaseRefreshActivity.this.onItemLongClick(v, pos);
                }
            });
        }
    }

    protected void setLoadMoreEnabled(boolean enabled) {//是否开启加载更多
        if (null != lRecyclerView) {
            lRecyclerView.setLoadMoreEnabled(enabled);
        }
    }

    protected void setPullRefreshEnabled(boolean enabled) {//是否开启下拉刷新
        if (null != lRecyclerView) {
            lRecyclerView.setPullRefreshEnabled(enabled);
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

    protected void onItemClick(View v, int position) {

    }

    protected void onItemLongClick(View v, int position) {

    }

    protected abstract RecyclerView.Adapter initAdapter();//初始化adapter

    protected abstract LRecyclerView findRecyclerView();//初始化RecyclerView

    protected abstract void reqFirst();//下拉刷新,请求第一页数据

    protected abstract void reqMore();//加载更多数据

//    protected abstract void reReq();//加载更多时失败,重新加载当页码数据
}
