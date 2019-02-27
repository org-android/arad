package com.ruitu.player18.radio;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruitu.arad.base.BaseActivity;
import com.ruitu.arad.util.AnimationUtil;
import com.ruitu.arad.util.ScreenUtils;
import com.ruitu.arad.util.SizeUtils;
import com.ruitu.arad.util.ToastUtils;
import com.ruitu.player18.R;
import com.ruitu.player18.bean.MusicInfo;
import com.ruitu.player18.mvp.RadioModel;
import com.ruitu.player18.mvp.RadioPresenter;

import java.util.ArrayList;
import java.util.List;

public class FmOnlineActivity extends BaseActivity<RadioPresenter, RadioModel> {
    private ImageView iv_speaker, iv_menu;//扬声器,菜单
    private ImageView iv_up, iv_down, iv_left, iv_right, iv_play;//上下左右播放
    private RelativeLayout rl_window, rl_music_list;//最外层的布局,音乐列表
    private RecyclerView rcv_music_list;//音乐列表
    private TextView tv_close;//关闭音乐列表

    private ContentResolver contentResolver;
    private List<MusicInfo> musicInfoList = new ArrayList<>();
    private MusicListAdapter adapter;

    //剑鱼音乐,http://r.yibo.so/live/5746ac3f934ff0e41b1e19b5.m3u8 【听音乐推荐首选】

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_fm_online;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rl_window = findViewById(R.id.rl_window);
        rcv_music_list = findViewById(R.id.rcv_music_list);
        rl_music_list = findViewById(R.id.rl_music_list);
        iv_speaker = findViewById(R.id.iv_speaker);
        iv_menu = findViewById(R.id.iv_menu);
        iv_up = findViewById(R.id.iv_up);
        iv_down = findViewById(R.id.iv_down);
        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);
        iv_play = findViewById(R.id.iv_play);
        tv_close = findViewById(R.id.tv_close);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_speaker.getLayoutParams();
        params.width = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(140);
        params.height = params.width;
        iv_speaker.setLayoutParams(params);

        adapter = new MusicListAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcv_music_list.setLayoutManager(manager);
        rcv_music_list.setAdapter(adapter);

        setHeadTitle("在线FM");
        contentResolver = getContentResolver();
        p.scanMusiList(contentResolver);

        setListeners();
    }

    private void setListeners() {
        rl_window.setOnClickListener(this);
        iv_menu.setOnClickListener(this);
        iv_up.setOnClickListener(this);
        iv_down.setOnClickListener(this);
        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        tv_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == rl_window) {
            rl_music_list.setVisibility(View.GONE);
        }
        if (v == tv_close){
            rl_music_list.setVisibility(View.GONE);
        }
        if (v == iv_menu) {
            if (rl_music_list.getVisibility() == View.GONE) {
                AnimationUtil.startAlphaAnima(rl_music_list, 0, 1);
                rl_music_list.setVisibility(View.VISIBLE);
            }
        }
        if (v == iv_up) {
            ToastUtils.showShortSafe("音量加");
        }
        if (v == iv_down) {
            ToastUtils.showShortSafe("音量减");
        }
        if (v == iv_left) {
            ToastUtils.showShortSafe("上一曲");
        }
        if (v == iv_right) {
            ToastUtils.showShortSafe("下一曲");
        }
        if (v == iv_play) {//播放
            ToastUtils.showShortSafe("播放");
        }
    }

    @Override
    public void onReqComplete(int code, boolean isOk, Object data) {
        if (code == 1) {
            musicInfoList = (List<MusicInfo>) data;
            adapter.setDataList(musicInfoList);
        }
    }
}
