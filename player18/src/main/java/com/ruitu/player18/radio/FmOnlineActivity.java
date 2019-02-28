package com.ruitu.player18.radio;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ruitu.arad.base.BaseActivity;
import com.ruitu.arad.base.base_list.ListBaseAdapter;
import com.ruitu.arad.util.AnimationUtil;
import com.ruitu.arad.util.HandlerUtil;
import com.ruitu.arad.util.Logg;
import com.ruitu.arad.util.ToastUtils;
import com.ruitu.player18.R;
import com.ruitu.player18.bean.MusicInfo;
import com.ruitu.player18.mvp.RadioModel;
import com.ruitu.player18.mvp.RadioPresenter;
import com.ruitu.player18.util.MediaUtil;

import java.util.ArrayList;
import java.util.List;

//剑鱼音乐,http://r.yibo.so/live/5746ac3f934ff0e41b1e19b5.m3u8 【听音乐推荐首选】
public class FmOnlineActivity extends BaseActivity<RadioPresenter, RadioModel> implements HandlerUtil.OnReceiveMessageListener {
    private ImageView iv_speaker, iv_menu;//扬声器,菜单
    private ImageView iv_left, iv_right, iv_play;//上下一曲播放
    private RelativeLayout rl_window, rl_music_list;//最外层的布局,音乐列表
    private RecyclerView rcv_music_list;//音乐列表
    private TextView tv_close, tv_time, tv_curr_time;//关闭音乐列表,音乐时间,当前播放时间
    private TextView tv_name, tv_artist;//歌曲名字(大红字),艺术家
    private AppCompatSeekBar seek_bar;

    private ContentResolver contentResolver;
    private MediaPlayer player;
    private List<MusicInfo> musicInfoList = new ArrayList<>();
    private MusicInfo curMusic;//记录当前操作或者播放的音乐
    private int currSeekBarProgress = 0;//记录当前SeekBar的进度
    private MusicListAdapter adapter;

    private boolean isPlayed = false;//是否播放过音乐

    private HandlerUtil.HandlerHolder handlerHolder;

    @Override
    public void handlerMessage(Message msg) {
        if (msg.what == 19022851) {
            seek_bar.setProgress(player.getCurrentPosition());
            handlerHolder.sendEmptyMessageDelayed(19022851, 500);
        }
    }

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
        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);
        iv_play = findViewById(R.id.iv_play);
        tv_close = findViewById(R.id.tv_close);
        seek_bar = findViewById(R.id.seek_bar);
        tv_curr_time = findViewById(R.id.tv_curr_time);
        tv_time = findViewById(R.id.tv_time);
        tv_name = findViewById(R.id.tv_name);
        tv_artist = findViewById(R.id.tv_artist);

//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_speaker.getLayoutParams();
//        params.width = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(130);
//        params.height = params.width;
//        iv_speaker.setLayoutParams(params);

        adapter = new MusicListAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rcv_music_list.setLayoutManager(manager);
        rcv_music_list.setAdapter(adapter);

        setHeadTitle("音悦台");
        contentResolver = getContentResolver();
        p.scanMusiList(contentResolver);

        player = new MediaPlayer();
        handlerHolder = new HandlerUtil.HandlerHolder(this);
        setListeners();
    }

    private void setListeners() {
        rl_window.setOnClickListener(this);
        iv_menu.setOnClickListener(this);
        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        tv_close.setOnClickListener(this);
        adapter.setOnItemClickListener(new ListBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                setPlayImgIcon(position);
                boolean isSameSong = p.playIndex == position;
                p.play(player, musicInfoList, position, isSameSong);
            }
        });
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_curr_time.setText(MediaUtil.formatTime(i));
                if (b) {
                    ToastUtils.showShortSafe(MediaUtil.formatTime(i));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                currSeekBarProgress = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
            }
        });
    }

    //设置正在播放的音乐显示标识
    private void setPlayImgIcon(int position) {
        for (int i = 0; i < musicInfoList.size(); i++) {
            if (i == position) {
                musicInfoList.get(i).setPlaying(true);
            } else {
                musicInfoList.get(i).setPlaying(false);
            }
        }
        adapter.setDataList(musicInfoList);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == rl_window) {
            rl_music_list.setVisibility(View.GONE);
        }
        if (v == tv_close) {
            rl_music_list.setVisibility(View.GONE);
        }
        if (v == iv_menu) {
            if (rl_music_list.getVisibility() == View.GONE) {
                AnimationUtil.startAlphaAnima(rl_music_list, 0, 1);
                rl_music_list.setVisibility(View.VISIBLE);
            }
        }
        if (v == iv_left) {
            p.playPrevious(player, musicInfoList);
        }
        if (v == iv_right) {
            p.playNext(player, musicInfoList);
        }
        if (v == iv_play) {//播放
            if (player.isPlaying()) {
                iv_play.setImageResource(R.mipmap.radio_play);
                player.pause();
            } else {
                if (isPlayed) {
                    iv_play.setImageResource(R.mipmap.radio_pause);
                    player.start();
                } else {
                    p.play(player, musicInfoList, 0, false);
                    iv_play.setImageResource(R.mipmap.radio_pause);
                }
            }
        }
    }

    @Override
    public void onReqComplete(int code, boolean isOk, Object data) {
        if (code == 1) {
            musicInfoList = (List<MusicInfo>) data;
            adapter.setDataList(musicInfoList);
        }
        if (code == 2) {//开始播放歌曲
            isPlayed = true;
            int index = (int) data;
            curMusic = musicInfoList.get(index);
            ToastUtils.showShortSafe("正在播放:" + curMusic.getTitle()
                    + "  艺术家:" + curMusic.getArtist());

            tv_name.setText(curMusic.getTitle());
            tv_artist.setText("艺术家：" + curMusic.getArtist());
            Bitmap bitmap = MediaUtil.getAlbumArt(this, curMusic.getAlbumId());
            if (null != bitmap) {
                iv_speaker.setImageBitmap(bitmap);
            } else {
                iv_speaker.setImageResource(R.mipmap.qq_music_icon);
            }

            AnimationUtil.startRotateAnimation(iv_speaker, 9900);

            tv_time.setText(MediaUtil.formatTime(player.getDuration()));
            iv_play.setImageResource(R.mipmap.radio_pause);
            rcv_music_list.scrollToPosition(index);
            setPlayImgIcon(index);
            seek_bar.setMax((int) curMusic.getDuration());
            handlerHolder.sendEmptyMessage(19022851);
        }
        if (code == 3) {
            TimedText timedText = (TimedText) data;
            Logg.i("timedText.getText() = " + timedText.getText());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerHolder.removeMessages(19022851);
        if (null != player) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
