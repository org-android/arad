package com.ruitu.player18.mvp;

import android.content.ContentResolver;
import android.media.MediaPlayer;
import android.media.TimedText;

import com.ruitu.arad.base.BasePresenter;
import com.ruitu.arad.util.ToastUtils;
import com.ruitu.player18.bean.MusicInfo;
import com.ruitu.player18.radio.FmOnlineActivity;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RadioPresenter extends BasePresenter<FmOnlineActivity, RadioModel> {
    public Disposable scanMusiList(ContentResolver contentResolver) {
        return m.scanMusiList(contentResolver).subscribe(new Consumer<List<MusicInfo>>() {
            @Override
            public void accept(List<MusicInfo> musicInfos) throws Exception {
                v.hideProgress();
                v.onReqComplete(1, true, musicInfos);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                v.hideProgress();
                ToastUtils.showShortSafe("扫描音乐发生错误");
                throwable.printStackTrace();
            }
        });
    }

    public int playIndex = 0;//记录当前播放的位置

    public void play(final MediaPlayer player, final List<MusicInfo> musicInfoList, final int index, boolean isSameSong) {
        if (isSameSong) {//是否是同一首音乐
            return;
        }
        String path = musicInfoList.get(index).getUrl();
        playIndex = index;
        try {
            player.reset();
            player.setDataSource(path);
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    v.onReqComplete(2, true, playIndex);
                }
            });
            player.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
                @Override
                public void onTimedText(MediaPlayer mediaPlayer, TimedText timedText) {
                    v.onReqComplete(3, true, timedText);
                }
            });
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    playNext(mediaPlayer, musicInfoList);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playNext(MediaPlayer mediaPlayer, List<MusicInfo> musicInfoList) {
        playIndex++;
        if (playIndex >= musicInfoList.size()) {
            playIndex = 0;
        }
        play(mediaPlayer, musicInfoList, playIndex, false);
    }

    public void playPrevious(MediaPlayer mediaPlayer, List<MusicInfo> musicInfoList) {
        playIndex--;
        if (playIndex < 0) {
            playIndex = musicInfoList.size() - 1;
        }
        play(mediaPlayer, musicInfoList, playIndex, false);
    }
}
