package com.ruitu.player18.tv;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.ruitu.arad.base.BaseActivity;
import com.ruitu.arad.support.view.MeasuredGridView;
import com.ruitu.arad.util.AnimationUtil;
import com.ruitu.arad.util.HandlerUtil;
import com.ruitu.arad.util.ScreenUtils;
import com.ruitu.arad.util.SizeUtils;
import com.ruitu.arad.util.ToastUtils;
import com.ruitu.arad.util.UIUtils;
import com.ruitu.player18.R;
import com.ruitu.player18.bean.TvItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TvActivity extends BaseActivity implements HandlerUtil.OnReceiveMessageListener {
    private TextView tv_choose_tv, tv_time, tv_name;//选台,时间,名称
    private ImageView iv_full_screen;//全屏切换
    private VideoView videoView;
    private RelativeLayout rl_container, rl_tv_list;//播放器videoView的容器,电视节目列表
    private LinearLayout ll_title, ll_controller;//播放器的标题栏,控制栏
    private MeasuredGridView gv_tv_list;//电视节目列表
    private ListView lv_tv_list;//全屏选台的电视节目列表

    private TvGridAdapter adapter;
    private TvGridAdapter adapter2;
    private List<TvItem> tvItemList = new ArrayList<>();
    private TvItem currTvItem;//当前播放的电视节目
    private boolean isTvPlaying = false;//是否正在播放电视节目
    private int currVideoWidth = 0;//当前视频宽
    private int currVideoHeight = 0;//当前视频高

    private HandlerUtil.HandlerHolder handlerHolder = new HandlerUtil.HandlerHolder(this);

    //    private String video_url = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";

    private void initTvItems() {
        tvItemList.add(new TvItem("cctv1", "http://cctvtxyh5c.liveplay.myqcloud.com/wstv/cctv1_2/index.m3u8"));
        tvItemList.add(new TvItem("cctv13", "http://liveali.ifeng.com/live/CCTV.m3u8"));
        tvItemList.add(new TvItem("第一财经", "https://w1live.livecdn.yicai.com/live/cbn.m3u8"));
        tvItemList.add(new TvItem("浙江新闻", "http://l.cztvcloud.com/channels/lantian/channel13/360p.m3u8"));
        tvItemList.add(new TvItem("深圳卫视", "http://www.szmgiptv.com:14436/hls/07.m3u8"));
        tvItemList.add(new TvItem("湖北卫视", "http://live.cjyun.org/video/s10008-hbws2018/index.m3u8"));
        tvItemList.add(new TvItem("南方卫视", "http://nclive.grtn.cn/tvs2/sd/live.m3u8"));
        tvItemList.add(new TvItem("凤凰中文", "http://117.169.120.217:8080/live/fhchinese/.m3u8"));
    }

    @Override
    public void handlerMessage(Message msg) {
        if (msg.what == 19022651) {
            tv_time.setText("" + new SimpleDateFormat("M月d日  HH:mm:ss").format(new Date()));
            handlerHolder.sendEmptyMessageDelayed(19022651, 1000);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_tv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏,无状态栏
        super.onCreate(savedInstanceState);
        videoView = findViewById(R.id.videoView);
        rl_container = findViewById(R.id.rl_container);
        rl_tv_list = findViewById(R.id.rl_tv_list);
        gv_tv_list = findViewById(R.id.gv_tv_list);
        iv_full_screen = findViewById(R.id.iv_full_screen);
        tv_choose_tv = findViewById(R.id.tv_choose_tv);
        tv_time = findViewById(R.id.tv_time);
        tv_name = findViewById(R.id.tv_name);
        ll_title = findViewById(R.id.ll_title);
        ll_controller = findViewById(R.id.ll_controller);
        lv_tv_list = findViewById(R.id.lv_tv_list);

        initTvItems();
        adapter = new TvGridAdapter(this);
        int column = tvItemList.size() % 2 == 0 ? tvItemList.size() / 2 : tvItemList.size() / 2 + 1;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gv_tv_list.getLayoutParams();
        params.width = column * SizeUtils.dp2px(120 + 10) - SizeUtils.dp2px(10);
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        gv_tv_list.setLayoutParams(params);
        gv_tv_list.setColumnWidth(SizeUtils.dp2px(120));
        gv_tv_list.setNumColumns(column);
        gv_tv_list.setStretchMode(GridView.NO_STRETCH);
        gv_tv_list.setAdapter(adapter);
        adapter.setTvItemList(tvItemList);

        adapter2 = new TvGridAdapter(this);
        lv_tv_list.setAdapter(adapter2);
        adapter2.setTvItemList(tvItemList);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//默认先给个竖屏
        handlerHolder.sendEmptyMessage(19022651);

        setListeners();
    }

    private void play(String video_url) {
        isTvPlaying = false;
        hideProgress();
        final Uri uri = Uri.parse(video_url);
        videoView.setVideoURI(uri);//设置视频路径
        showProgressWithText(true, "加载电视节目...");
        tv_name.setText("当前播放：" + currTvItem.getName());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == iv_full_screen) {
            if (isTvPlaying) {
                changeScreen();
            } else {
                ToastUtils.showShortSafe("先选个频道随便看看吧");
            }
        }
        if (v == tv_choose_tv) {//选台
            lv_tv_list.setVisibility(View.VISIBLE);
        }
        if (v == videoView) {
            lv_tv_list.setVisibility(View.GONE);
            if (ll_title.getVisibility() == View.VISIBLE) {
                showOrHideController(false);
            } else {
                showOrHideController(true);
            }
        }
    }

    private void setListeners() {
        iv_full_screen.setOnClickListener(this);
        tv_choose_tv.setOnClickListener(this);
        gv_tv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currTvItem = tvItemList.get(position);
                play(currTvItem.getUrl());
            }
        });
        lv_tv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currTvItem = tvItemList.get(position);
                play(currTvItem.getUrl());
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                String message = "非常抱歉,视频加载失败";
                UIUtils.showAlertDialog(getSupportFragmentManager(), "温馨提示", message
                        , "重新加载", "返回"
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                play(currTvItem.getUrl());
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                return false;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                showProgress(false);
                videoView.start(); //开始播放视频
                isTvPlaying = true;

                currVideoWidth = mp.getVideoWidth();
                currVideoHeight = mp.getVideoHeight();
                setVideoViewSize(isScreenPortrait());
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //播放完成回调
            }
        });
        videoView.setOnClickListener(this);
    }

    private void setVideoViewSize(boolean isPortrait) {//设置VideoView的尺寸
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rl_container.getLayoutParams();
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
        if (isPortrait) {
            params.width = ScreenUtils.getScreenWidth();
            params.height = SizeUtils.dp2px(200);

            params2.height = SizeUtils.dp2px(200);
            params2.width = currVideoWidth * params2.height / currVideoHeight;
            params2.addRule(RelativeLayout.CENTER_IN_PARENT);

        } else {
            params.width = ScreenUtils.getScreenWidth();
            params.height = ScreenUtils.getScreenHeight();

            params2.height = ScreenUtils.getScreenHeight();
            params2.width = currVideoWidth * params2.height / currVideoHeight;
            params2.addRule(RelativeLayout.CENTER_IN_PARENT);

        }
        rl_container.setLayoutParams(params);
        videoView.setLayoutParams(params2);
    }

    private void showOrHideController(boolean isShow) {//显示或者隐藏控制面板
        if (isShow) {
            AnimationUtil.startAlphaAnima(ll_controller, 0, 1);
            AnimationUtil.startAlphaAnima(ll_title, 0, 1);
            ll_controller.setVisibility(View.VISIBLE);
            ll_title.setVisibility(View.VISIBLE);
        } else {
            ll_controller.setVisibility(View.GONE);
            ll_title.setVisibility(View.GONE);
        }
    }

    private void changeScreen() {
        //判断当前屏幕方向
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private boolean isScreenPortrait() {//屏幕是否是竖屏
        return getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public void onBackPressed() {//点击返回的时候先切换竖屏
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            super.onBackPressed();
        }
    }

    @Override//横竖屏切换的时候执行
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setVideoViewSize(isScreenPortrait());
        lv_tv_list.setVisibility(View.GONE);
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            tv_choose_tv.setVisibility(View.VISIBLE);
            rl_tv_list.setVisibility(View.GONE);
        } else {
            tv_choose_tv.setVisibility(View.GONE);
            rl_tv_list.setVisibility(View.VISIBLE);
        }
    }
}

