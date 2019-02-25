package com.ruitu.player18.tv;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruitu.player18.R;
import com.ruitu.player18.bean.TvItem;

import java.util.List;

public class TvGridAdapter extends BaseAdapter {
    private Activity activity;
    private List<TvItem> tvItemList;

    public TvGridAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setTvItemList(List<TvItem> tvItemList) {
        this.tvItemList = tvItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == tvItemList ? 0 : tvItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        TvViewHolder holder = null;
        if (null != convertView) {
            holder = (TvViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(activity, R.layout.item_tv_list, null);
            holder = new TvViewHolder();
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.iv_logo = convertView.findViewById(R.id.iv_logo);
            convertView.setTag(holder);
        }
        return convertView;
    }

    class TvViewHolder {
        TextView tv_name;
        ImageView iv_logo;
    }
}
