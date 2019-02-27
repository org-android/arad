package com.ruitu.player18.mvp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.ruitu.arad.api.RxUtil;
import com.ruitu.arad.base.BaseModel;
import com.ruitu.player18.bean.MusicInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class RadioModel extends BaseModel {

    public Observable<List<MusicInfo>> scanMusiList(final ContentResolver contentResolver) {
        return Observable.create(new ObservableOnSubscribe<List<MusicInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MusicInfo>> emitter) throws Exception {
                try {
                    Cursor mCursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,   //路径
                            new String[]{MediaStore.Audio.Media._ID,    //写入我想要获得的信息（列）
                                    MediaStore.Audio.Media.TITLE,
                                    MediaStore.Audio.Media.ALBUM,
                                    MediaStore.Audio.Media.DISPLAY_NAME,
                                    MediaStore.Audio.Media.ARTIST,
                                    MediaStore.Audio.Media.DURATION,
                                    MediaStore.Audio.Media.SIZE,
                                    MediaStore.Audio.Media.DATA}, null, null, null);

                    List<MusicInfo> mMusicInfos = new ArrayList<>();
                    for (int i = 0; i < mCursor.getCount(); ++i) {
                        MusicInfo musicInfo = new MusicInfo();  //MusicInfo类是数据储存单元
                        mCursor.moveToNext();   //读取下一行，moveToNext()有boolean返回值，执行成功返回ture,反之false，可用于判断是否读取完毕。

                        long id = mCursor.getLong(0);
                        String title = mCursor.getString(1);
                        String album = mCursor.getString(2);
                        String displayName = mCursor.getString(3);
                        String artist = mCursor.getString(4);
                        long duration = mCursor.getLong(5);
                        long size = mCursor.getLong(6);
                        String url = mCursor.getString(7);   //转存数据

                        musicInfo.setTitle(title);
                        musicInfo.setId(id);
                        musicInfo.setAlbum(album);
                        musicInfo.setArtist(artist);
                        musicInfo.setSize(size);
                        musicInfo.setDisplayName(displayName);
                        musicInfo.setDuration(duration);
                        musicInfo.setUrl(url);  //存到数据单元

                        mMusicInfos.add(musicInfo);  //添加到List
                    }

                    if (!mCursor.isClosed()) {
                        mCursor.close();
                    }

                    emitter.onNext(mMusicInfos);

                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).compose(RxUtil.<List<MusicInfo>>subIoObMain());
    }
}
