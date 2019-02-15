package com.ruitu.router_module.util;

import android.os.Handler;
import android.os.Looper;

import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class RcvUtils {

    //延时取消刷新LRecyclerView
    public static void refreshCompleteDelay(final LRecyclerView recyclerView, final OnRefreshListener onRefreshListener) {
        try {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.refreshComplete(20);
                    if (null != onRefreshListener) {
                        onRefreshListener.onRefreshComplete();
                    }
                }
            }, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnRefreshListener {
        void onRefreshComplete();
    }
}
