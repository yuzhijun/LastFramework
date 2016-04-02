package com.medical.lenovo.lastframework.utils.RxImageLoader;

import rx.Observable;

/**
 * Created by lenovo on 2016/4/2.
 */
public abstract class CacheObservable {
    public abstract Observable<Data> getObservable(String url);
}
