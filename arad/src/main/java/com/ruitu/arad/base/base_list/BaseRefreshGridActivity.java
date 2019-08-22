package com.ruitu.arad.base.base_list;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.ruitu.arad.Arad;
import com.ruitu.arad.R;
import com.ruitu.arad.base.BaseModel;
import com.ruitu.arad.base.BasePresenter;

public abstract class BaseRefreshGridActivity<T extends BasePresenter, E extends BaseModel>
        extends BaseRefreshActivity<T, E> {

    @Override
    protected void initRecyclerView() {
        if (null != lRecyclerView) {

//            CustomRefreshHeader header = new CustomRefreshHeader(this);
//            header.setHintTextColor(R.color.l_rcv_color);
//            header.setViewBackgroundColor(R.color.white);
            if (null != Arad.refreshHeader) {// new CustomRefreshHeader(context)
                lRecyclerView.setRefreshHeader(Arad.refreshHeader);
            }

            lRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            lRecyclerView.setAdapter(lAdapter);

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
        }

        if (null != lAdapter) {
            //设置点击事件
            lAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    BaseRefreshGridActivity.this.onItemClick(v, position);
                }
            });
            lAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public void onItemLongClick(View v, int position) {
                    BaseRefreshGridActivity.this.onItemLongClick(v, position);
                }
            });

//            lRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
//                @Override
//                public void reload() {
//                    reReq();
//                }
//            });
        }
    }
}
