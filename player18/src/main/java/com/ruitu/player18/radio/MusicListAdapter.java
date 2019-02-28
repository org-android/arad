package com.ruitu.player18.radio;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruitu.arad.base.base_list.ListBaseAdapter;
import com.ruitu.arad.base.base_list.SuperViewHolder;
import com.ruitu.player18.R;
import com.ruitu.player18.bean.MusicInfo;
import com.ruitu.player18.util.MediaUtil;

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
        ImageView iv_logo = holder.getView(R.id.iv_logo);//专辑图
        ImageView iv_play = holder.getView(R.id.iv_play);//播放标识
        TextView tv_name = holder.getView(R.id.tv_name);//
        TextView tv_artist = holder.getView(R.id.tv_artist);//

        MusicInfo m = mDataList.get(position);

        tv_name.setText(m.getTitle() + "");
        tv_artist.setText(m.getArtist() + "");
        Bitmap bitmap = MediaUtil.getAlbumArt(mContext, m.getAlbumId());
        if (null != bitmap) {
            iv_logo.setImageBitmap(bitmap);
        } else {
            iv_logo.setImageResource(R.mipmap.qq_music_icon);
        }
        if (m.isPlaying()) {//是否正在播放
            iv_play.setVisibility(View.VISIBLE);
        } else {
            iv_play.setVisibility(View.INVISIBLE);
        }
    }
}
