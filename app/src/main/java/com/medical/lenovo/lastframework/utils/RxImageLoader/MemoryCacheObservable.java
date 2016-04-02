package com.medical.lenovo.lastframework.utils.RxImageLoader;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lenovo on 2016/4/3.
 */
public class MemoryCacheObservable extends CacheObservable{
    // 获取应用程序最大可用内存
    public int maxMemory = (int) Runtime.getRuntime().maxMemory();
    public int cacheSize = maxMemory / 8;
    // 设置图片缓存大小为程序最大可用内存的1/8
    private MemoryCache<String> mCache = new MemoryCache<>(cacheSize);

    @Override
    public Observable<Data> getObservable(final String url) {

        return Observable.create(new Observable.OnSubscribe<Data>() {
            @Override
            public void call(Subscriber<? super Data> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(new Data(mCache.get(url), url));
                    subscriber.onCompleted();
                }
            }
        });
    }

    public void putData(Data data) {
        mCache.put(data.url, data.bitmap);
    }
}
