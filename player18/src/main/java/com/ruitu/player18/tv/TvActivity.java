package com.ruitu.player18.tv;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.ruitu.arad.base.BaseActivity;
import com.ruitu.arad.util.ScreenUtils;
import com.ruitu.arad.util.SizeUtils;
import com.ruitu.arad.util.UIUtils;
import com.ruitu.player18.R;

public class TvActivity extends BaseActivity {

    private VideoView videoView;
    private RelativeLayout rl_container;//播放器videoView的容器
    private GridView gv_tv_list;//电视节目列表

    //    private String video_url = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
//    private String video_url = "http://cctvtxyh5c.liveplay.myqcloud.com/wstv/cctv1_2/index.m3u8";//cctv1
//    private String video_url = "http://liveali.ifeng.com/live/CCTV.m3u8";//cctv13
//    private String video_url = "https://w1live.livecdn.yicai.com/live/cbn.m3u8";//第一财经
//    private String video_url = "http://l.cztvcloud.com/channels/lantian/channel13/360p.m3u8";//浙江卫视
//    private String video_url = "http://cietv.com/dianshizhibo/cietv.m3u8";//当前直播地址
//    private String video_url = "http://www.szmgiptv.com:14436/hls/07.m3u8";//深圳卫视
//    private String video_url = "http://live.cjyun.org/video/s10008-hbws2018/index.m3u8";//湖北卫视
//    private String video_url = "http://nclive.grtn.cn/tvs2/sd/live.m3u8";//南方卫视
    private String video_url = "http://117.169.120.217:8080/live/fhchinese/.m3u8";//凤凰中文

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
        gv_tv_list = findViewById(R.id.gv_tv_list);

        //网络视频
        final Uri uri = Uri.parse(video_url);
        videoView.setVideoURI(uri);//设置视频路径

        showProgressWithText(true, "视频加载中...");

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                String message = "非常抱歉,视频加载失败";
                UIUtils.showAlertDialog(getSupportFragmentManager(), "温馨提示", message
                        , "重新加载", "返回"
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                videoView.setVideoURI(uri);//设置视频路径
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

                int videoHeight = mp.getVideoHeight();
                int videoWidth = mp.getVideoWidth();

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rl_container.getLayoutParams();
                params.width = ScreenUtils.getScreenWidth();
                params.height = SizeUtils.dp2px(200);
                rl_container.setLayoutParams(params);

                RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
                params2.height = SizeUtils.dp2px(200);
                params2.width = videoWidth * params2.height / videoHeight;
                videoView.setLayoutParams(params);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //播放完成回调
            }
        });
    }
}

