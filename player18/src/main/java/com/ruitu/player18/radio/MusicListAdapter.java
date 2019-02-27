package com.ruitu.player18.radio;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruitu.arad.base.base_list.ListBaseAdapter;
import com.ruitu.arad.base.base_list.SuperViewHolder;
import com.ruitu.player18.R;
import com.ruitu.player18.bean.MusicInfo;

public class MusicListAdapter extends ListBaseAdapter<MusicInfo> {
    public MusicListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_music_list;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        ImageView iv_logo = holder.getView(R.id.iv_logo);//
        TextView tv_name = holder.getView(R.id.tv_name);//
        TextView tv_artist = holder.getView(R.id.tv_artist);//

        MusicInfo m = mDataList.get(position);

        tv_name.setText(m.getDisplayName() + "");
        tv_artist.setText(m.getArtist() + "");
    }
}
