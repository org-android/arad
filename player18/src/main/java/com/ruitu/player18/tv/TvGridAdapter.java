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
    public int type = 0;// 0 竖屏电视节目列表 1 横屏节目列表
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
        if (null == convertView) {
            if (type == 1) {
                convertView = View.inflate(activity, R.layout.item_tv_list_wide, null);
            } else {
                convertView = View.inflate(activity, R.layout.item_tv_list, null);
            }
            holder = new TvViewHolder();
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.iv_logo = convertView.findViewById(R.id.iv_logo);
            convertView.setTag(holder);
        } else {
            holder = (TvViewHolder) convertView.getTag();
        }
        TvItem tvItem = tvItemList.get(position);
        holder.tv_name.setText(tvItem.getName());
        if (tvItem.getType() == 3) {//说明是正在播放的
            holder.iv_logo.setImageResource(R.mipmap.tv_play3);
            holder.iv_logo.setVisibility(View.VISIBLE);
        } else if (tvItem.getType() == 2) {//说明是可以播放的
            holder.iv_logo.setImageResource(R.mipmap.tv_play1);
            holder.iv_logo.setVisibility(View.VISIBLE);
        }
//        else if (tvItem.getType() == 1) {//说明是正在测试的
////            holder.iv_logo.setImageResource(R.mipmap.tv_play3);
//            holder.iv_logo.setVisibility(View.INVISIBLE);
//        }
        else {//不能播放的
            holder.iv_logo.setImageResource(R.mipmap.tv_play0);
            holder.iv_logo.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    class TvViewHolder {
        TextView tv_name;
        ImageView iv_logo;
    }
}
