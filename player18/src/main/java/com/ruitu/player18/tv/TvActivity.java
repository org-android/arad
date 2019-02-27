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
import com.ruitu.arad.util.AnimationUtil;
import com.ruitu.arad.util.HandlerUtil;
import com.ruitu.arad.util.ScreenUtils;
import com.ruitu.arad.util.SizeUtils;
import com.ruitu.arad.util.ToastUtils;
import com.ruitu.arad.util.UIUtils;
import com.ruitu.player18.R;
import com.ruitu.player18.bean.TvItem;
import com.ruitu.player18.mvp.TvModel;
import com.ruitu.player18.mvp.TvPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TvActivity extends BaseActivity<TvPresenter, TvModel> implements HandlerUtil.OnReceiveMessageListener {
    public final static int LINE_NUM = 3;//频道行数

    private TextView tv_choose_tv, tv_time, tv_name;//选台,时间,名称
    private ImageView iv_full_screen;//全屏切换
    private VideoView videoView;
    private RelativeLayout rl_container, rl_tv_list;//播放器videoView的容器,电视节目列表
    private LinearLayout ll_title, ll_controller;//播放器的标题栏,控制栏
    private GridView gv_tv_list;//电视节目列表
    private ListView lv_tv_list;//全屏选台的电视节目列表

    private TvGridAdapter adapter;
    private TvGridAdapter adapter2;
    private List<TvItem> tvItemList = new ArrayList<>();
    private TvItem currTvItem;//当前播放的电视节目
    private boolean isTvPlaying = false;//是否正在播放电视节目
    private int currVideoWidth = 0;//当前视频宽
    private int currVideoHeight = 0;//当前视频高

    private HandlerUtil.HandlerHolder handlerHolder = new HandlerUtil.HandlerHolder(this);

    @Override
    public void handlerMessage(Message msg) {
        if (msg.what == 19022651) {
            tv_time.setText(new SimpleDateFormat("M月d日  HH:mm:ss").format(new Date()));
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

        p.initTvItemList();

        adapter = new TvGridAdapter(this);
        adapter2 = new TvGridAdapter(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//默认先给个竖屏
        handlerHolder.sendEmptyMessage(19022651);

        setListeners();
    }

    @Override
    public void onReqComplete(int code, boolean isOk, Object data) {
        if (code == 1) {
            tvItemList = (List<TvItem>) data;
            int column = tvItemList.size() % LINE_NUM == 0 ?
                    tvItemList.size() / LINE_NUM : tvItemList.size() / LINE_NUM + 1;
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gv_tv_list.getLayoutParams();
//        params.width = column * SizeUtils.dp2px(120 + 10) - SizeUtils.dp2px(10);
//        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
//        gv_tv_list.setLayoutParams(params);
//        int itemWidth = ScreenUtils.getScreenWidth() / 3;
//        gv_tv_list.setColumnWidth(itemWidth);
            gv_tv_list.setColumnWidth(SizeUtils.dp2px(140));
            gv_tv_list.setNumColumns(2);// column
            gv_tv_list.setStretchMode(GridView.NO_STRETCH);
            gv_tv_list.setAdapter(adapter);
            adapter.setTvItemList(tvItemList);

            adapter2.type = 1;//加载横屏电视节目布局
            lv_tv_list.setAdapter(adapter2);
            adapter2.setTvItemList(tvItemList);
        }
    }

    private void play(String video_url) {
        for (int i = 0; i < tvItemList.size(); i++) {
            if (tvItemList.get(i) != currTvItem && tvItemList.get(i).getType() == 3) {
                tvItemList.get(i).setType(2);//将其他正在播放的状态置空
            }
            if (currTvItem.getType() == 2) {//点击的是可以播放的
                currTvItem.setType(3);
            }
        }
        adapter.setTvItemList(tvItemList);
        adapter2.setTvItemList(tvItemList);

        isTvPlaying = false;
        hideProgress();
        final Uri uri = Uri.parse(video_url);
        videoView.setVideoURI(uri);//设置视频路径
        showProgressWithText(true, "准备播放" + currTvItem.getName());
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
            AnimationUtil.startTranlateAnimation(lv_tv_list
                    , ScreenUtils.getScreenWidth() + SizeUtils.dp2px(180), 0);
            lv_tv_list.setVisibility(View.VISIBLE);
        }
        if (v == videoView) {
            if (lv_tv_list.getVisibility() == View.VISIBLE) {
                lv_tv_list.setVisibility(View.GONE);
            } else {
                if (ll_title.getVisibility() == View.VISIBLE) {
                    showOrHideController(false);
                } else {
                    showOrHideController(true);
                }
            }
        }
    }

    private void setListeners() {
        videoView.setOnClickListener(this);
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
                hideProgress();
                String message = "非常抱歉,视频加载失败";
                UIUtils.showAlertDialog(getSupportFragmentManager(), "温馨提示", message
                        , "重新加载", "取消"
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                play(currTvItem.getUrl());
                            }
                        }, null);
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

