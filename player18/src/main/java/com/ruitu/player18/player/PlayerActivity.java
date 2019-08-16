//package com.ruitu.player18.player;
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.ruitu.arad.base.BaseActivity;
//import com.ruitu.player18.R;
//
//import io.vov.vitamio.MediaPlayer;
//import io.vov.vitamio.Vitamio;
//import io.vov.vitamio.widget.MediaController;
//import io.vov.vitamio.widget.VideoView;
//
//public class PlayerActivity extends BaseActivity {
//    private VideoView videoView;
//    private TextView percentTv;
//    private TextView netSpeedTv;
//    private int mVideoLayout = 0;
//    private String url0 = "http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8";
//    private String url1 = "http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";
//    private String url2 = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
//    private String url3 = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
//    private String url4 = "http://42.96.249.166/live/388.m3u8";
//    private String url5 = "http://live.3gv.ifeng.com/zixun.m3u8";
//
//    @Override
//    protected int getLayoutRes() {
//        return R.layout.activity_player;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //检查vitamio框架是否可用
////        if (!LibsChecker.checkVitamioLibs(this)) {
////            return;
////        }
////        setContentView(R.layout.activity_main);
//
//        percentTv = findViewById(R.id.buffer_percent);//显示缓冲百分比的TextView
//        netSpeedTv = findViewById(R.id.net_speed);//显示下载网速的TextView
//
//        if (Vitamio.isInitialized(this)) {
//            videoView = findViewById(R.id.vitamio);
//            videoView.setVideoURI(Uri.parse(url0));
//            videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
//            MediaController controller = new MediaController(this);
//            videoView.setMediaController(controller);
//            videoView.setBufferSize(10240); //设置视频缓冲大小。默认1024KB，单位byte
//            videoView.requestFocus();
//            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mediaPlayer) {
//                    // optional need Vitamio 4.0
//                    mediaPlayer.setPlaybackSpeed(1.0f);
//                    //mediaPlayer.setLooping(true);
//                }
//            });
//
//            videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
//                @Override
//                public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                    percentTv.setText("已缓冲：" + percent + "%");
//                }
//            });
//            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                @Override
//                public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                    switch (what) {
//                        //开始缓冲
//                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
//                            percentTv.setVisibility(View.VISIBLE);
//                            netSpeedTv.setVisibility(View.VISIBLE);
//                            mp.pause();
//                            break;
//                        //缓冲结束
//                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//                            percentTv.setVisibility(View.GONE);
//                            netSpeedTv.setVisibility(View.GONE);
//                            mp.start(); //缓冲结束再播放
//                            break;
//                        //正在缓冲
//                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
//                            netSpeedTv.setText("当前网速:" + extra + "kb/s");
//                            break;
//                    }
//                    return true;
//                }
//            });
//        }
//    }
//
//    public void changeLayout(View view) {
////        mVideoLayout++;
////        if (mVideoLayout == 4) {
////            mVideoLayout = 0;
////        }
////        switch (mVideoLayout) {
////            case 0:
////                mVideoLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
////                view.setBackgroundResource(R.mipmap.mediacontroller_sreen_size_100);
////                break;
////            case 1:
////                mVideoLayout = VideoView.VIDEO_LAYOUT_SCALE;
////                view.setBackgroundResource(R.mipmap.mediacontroller_screen_fit);
////                break;
////            case 2:
////                mVideoLayout = VideoView.VIDEO_LAYOUT_STRETCH;
////                view.setBackgroundResource(R.mipmap.mediacontroller_screen_size);
////                break;
////            case 3:
////                mVideoLayout = VideoView.VIDEO_LAYOUT_ZOOM;
////                view.setBackgroundResource(R.mipmap.mediacontroller_sreen_size_crop);
////
////                break;
////        }
////        videoView.setVideoLayout(mVideoLayout, 0);
//    }
//}
