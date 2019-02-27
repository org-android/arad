package com.ruitu.player18.mvp;

import com.ruitu.arad.api.RxUtil;
import com.ruitu.arad.base.BaseModel;
import com.ruitu.player18.bean.TvItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class TvModel extends BaseModel {
    public Observable<List<TvItem>> initTvItemList() {
        return Observable.create(new ObservableOnSubscribe<List<TvItem>>() {
            @Override
            public void subscribe(ObservableEmitter<List<TvItem>> emitter) throws Exception {
                List<TvItem> tvItemList = new ArrayList<>();
                //    private String video_url = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
                tvItemList.add(new TvItem(2, "测试频道", "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"));
                tvItemList.add(new TvItem(0, "cctv1", "http://cctvtxyh5c.liveplay.myqcloud.com/wstv/cctv1_2/index.m3u8"));
                tvItemList.add(new TvItem(0, "cctv1高清", "http://115.233.62.23/live.aishang.ctlcdn.com/00000110240127_1/encoder/3/playlist.m3u8"));
                tvItemList.add(new TvItem(2, "cctv3高清", "http://ivi.bupt.edu.cn/hls/cctv3hd.m3u8"));
                tvItemList.add(new TvItem(0, "cctv5", "http://115.233.62.9/live.aishang.ctlcdn.com/00000110240246_1/encoder/3/playlist.m3u8"));
                tvItemList.add(new TvItem(0, "cctv5高清", "http://ivi.bupt.edu.cn/hls/cctv5hd.m3u8"));
                tvItemList.add(new TvItem(2, "cctv5+高清", "http://ivi.bupt.edu.cn/hls/cctv5phd.m3u8"));
                tvItemList.add(new TvItem(2, "cctv6高清", "http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8"));
//                tvItemList.add(new TvItem(1, "cctv7", "http://115.233.62.9/live.aishang.ctlcdn.com/00000110240248_1/encoder/3/playlist.m3u8"));
//                tvItemList.add(new TvItem(1, "cctv8", "http://cctv8.vtime.cntv.cloudcdn.net:8500/cache/227_/seg0/index.m3u8"));
//                tvItemList.add(new TvItem(1, "cctv9", "http://115.233.62.9/live.aishang.ctlcdn.com/00000110240250_1/encoder/3/playlist.m3u8"));
//                tvItemList.add(new TvItem(1, "cctv10", "http://115.233.62.9/live.aishang.ctlcdn.com/00000110240251_1/encoder/3/playlist.m3u8"));
//                tvItemList.add(new TvItem(1, "cctv12", "http://115.233.62.9/live.aishang.ctlcdn.com/00000110240252_1/encoder/3/playlist.m3u8"));
                tvItemList.add(new TvItem(2, "cctv13", "http://liveali.ifeng.com/live/CCTV.m3u8"));
//                tvItemList.add(new TvItem(1, "cctv14", "http://115.233.62.9/live.aishang.ctlcdn.com/00000110240253_1/encoder/3/playlist.m3u8"));
//                tvItemList.add(new TvItem(1, "东方卫视", "http://60.214.208.202:9222/mweb/cmv/dfws.m3u8"));
                tvItemList.add(new TvItem(2, "兵团卫视", "http://v.btzx.com.cn:1935/live/weishi.stream/playlist.m3u8"));
                tvItemList.add(new TvItem(0, "山东卫视", "http://111.13.42.8/PLTV/88888888/224/3221225894/index.m3u8"));
                tvItemList.add(new TvItem(2, "浙江新闻", "http://l.cztvcloud.com/channels/lantian/channel13/360p.m3u8"));
                tvItemList.add(new TvItem(2, "深圳卫视", "http://www.szmgiptv.com:14436/hls/07.m3u8"));
                tvItemList.add(new TvItem(2, "湖北卫视", "http://live.cjyun.org/video/s10008-hbws2018/index.m3u8"));
//                tvItemList.add(new TvItem(1, "湖南卫视", "http://tv03.tsytv.com.cn/liveho/HunanHDm-500K.stream/chunklist.m3u8"));
                tvItemList.add(new TvItem(2, "南方卫视", "http://nclive.grtn.cn/tvs2/sd/live.m3u8"));
                tvItemList.add(new TvItem(2, "第一财经", "https://w1live.livecdn.yicai.com/live/cbn.m3u8"));
                tvItemList.add(new TvItem(2, "凤凰中文", "http://117.169.120.217:8080/live/fhchinese/.m3u8"));
//                tvItemList.add(new TvItem(1, "凤凰资讯", "http://ktv005.cdnak.ds.kylintv.net/nlds/kylin/pxinhd/as/live/pxinhd_4.m3u8"));
                tvItemList.add(new TvItem(2, "厦门影视", "http://cstv.live.wscdns.com/live/xiamen4/playlist.m3u8"));
                tvItemList.add(new TvItem(2, "万州影视文艺", "http://wanzhoulive.cbg.cn:8017/d4ceB1a/1000/live.m3u8"));
//                tvItemList.add(new TvItem(1, "中国交通", "http://r.gslb.lecloud.com/live/hls/20151111300009925/desc.m3u8"));
//                tvItemList.add(new TvItem(1, "中国电影", "http://ktv044.cdnak.ds.kylintv.net/nlds/kylin/chinamovie/as/live/chinamovie_4.m3u8"));
                emitter.onNext(tvItemList);
            }
        }).compose(RxUtil.<List<TvItem>>subIoObMain());
    }
}
