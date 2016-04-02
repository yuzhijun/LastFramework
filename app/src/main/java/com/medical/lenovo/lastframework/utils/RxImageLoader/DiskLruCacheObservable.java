package com.medical.lenovo.lastframework.utils.RxImageLoader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2016/4/3.
 */
public class DiskLruCacheObservable extends CacheObservable {
    private Context mContext;
    private File mCacheFile;
    private DiskLruCache mDiskLruCache;

    public DiskLruCacheObservable(Context context){
        this.mContext = context;
        mCacheFile = mContext.getCacheDir();
        try {
            mDiskLruCache = DiskLruCache.open(mCacheFile, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Observable<Data> getObservable(final String url) {

        return Observable.create(new Observable.OnSubscribe<Data>() {
            @Override
            public void call(Subscriber<? super Data> subscriber) {
                FileInputStream fileInputStream = null;
                try {
                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(url);
                    fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                    Data data = new Data(fileInputStream.getFD(), url);
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void putData(final Data data) {
        Observable.create(new Observable.OnSubscribe<Data>() {
            @Override
            public void call(Subscriber<? super Data> subscriber) {
                try {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(data.url);
                    BufferedOutputStream out = null;
                    if (editor != null){
                        out =  new BufferedOutputStream(editor.newOutputStream(0), 8 * 1024);
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    data.bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    InputStream in = new ByteArrayInputStream(baos.toByteArray());
                    int b;
                    while ((b = in.read()) != -1) {
                        out.write(b);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取当前应用程序的版本号。
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
