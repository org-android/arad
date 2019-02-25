package com.ruitu.player18.tv;

import android.content.Context;

import com.ruitu.arad.base.base_list.ListBaseAdapter;
import com.ruitu.arad.base.base_list.SuperViewHolder;
import com.ruitu.player18.bean.TvItem;

public class TvListAdapter extends ListBaseAdapter<TvItem> {
    public TvListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {

    }
}
