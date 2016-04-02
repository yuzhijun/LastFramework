package com.medical.lenovo.lastframework.utils.RxImageLoader;

import android.content.Context;
import android.util.Log;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by lenovo on 2016/4/3.
 */
public class CacheManager {
    private String TAG = CacheManager.class.getSimpleName();
    Context mContext;
    MemoryCacheObservable mMemoryCacheOvservable;
    DiskLruCacheObservable mDiskCacheObservable;
    NetCacheObservable mNetCacheObservable;

    public CacheManager(Context context){
        this.mContext = context;
        mMemoryCacheOvservable = new MemoryCacheObservable();
        mDiskCacheObservable = new DiskLruCacheObservable(mContext);
        mNetCacheObservable =new NetCacheObservable();
    }

    public Observable<Data> memory(String url) {
        return mMemoryCacheOvservable.getObservable(url)
                .compose(logSource("MEMORY"));
    }

    public Observable<Data> disk(String url) {
        return mDiskCacheObservable.getObservable(url)
                .filter(new Func1<Data, Boolean>() {
                    @Override
                    public Boolean call(Data data) {
                        return data.bitmap != null;
                    }
                }).doOnNext(new Action1<Data>() {
                    @Override
                    public void call(Data data) {
                        mMemoryCacheOvservable.putData(data);
                    }
                }).compose(logSource("DISK"));

    }

    public Observable<Data> network(String url) {
        return mNetCacheObservable.getObservable(url)
                .doOnNext(new Action1<Data>() {
                    @Override
                    public void call(Data data) {
                        mMemoryCacheOvservable.putData(data);
                        mDiskCacheObservable.putData(data);
                    }
                }).compose(logSource("NET"));
    }

    Observable.Transformer<Data, Data> logSource(final String source) {
        return new Observable.Transformer<Data, Data>() {
            @Override
            public Observable<Data> call(Observable<Data> dataObservable) {
                return dataObservable.doOnNext(new Action1<Data>() {
                    @Override
                    public void call(Data data) {
                        if (data != null && data.bitmap != null) {
                            Log.i(TAG,source + " has the data you are looking for!");
                        } else {
                            Log.i(TAG,source + " not has the data!");
                        }
                    }
                });
            }
        };
    }
}

