package com.ruitu.player18.player;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.ruitu.arad.base.BaseActivity;
import com.ruitu.arad.util.ScreenUtils;
import com.ruitu.arad.util.SizeUtils;
import com.ruitu.arad.util.UIUtils;
import com.ruitu.player18.R;

//    http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8
//    http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8
//    http://live.3gv.ifeng.com/zixun.m3u8 (直播)
public class VideoViewActivity extends BaseActivity {
    private VideoView videoView;
    private RelativeLayout rl_container;//播放器videoView的容器

    //    private String video_url = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
//    private String video_url = "http://cctvtxyh5c.liveplay.myqcloud.com/wstv/cctv1_2/index.m3u8";//cctv1
    private String video_url = "http://liveali.ifeng.com/live/CCTV.m3u8";//cctv13

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏,无状态栏
        super.onCreate(savedInstanceState);
        videoView = findViewById(R.id.videoView);
        rl_container = findViewById(R.id.rl_container);

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
