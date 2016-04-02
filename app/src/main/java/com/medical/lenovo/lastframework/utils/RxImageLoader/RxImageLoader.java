package com.medical.lenovo.lastframework.utils.RxImageLoader;

import android.content.Context;
import android.widget.ImageView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by lenovo on 2016/4/3.
 */
public class RxImageLoader {
    static CacheManager mCacheManager;

    public static void init(Context mContext) {
        mCacheManager = new CacheManager(mContext);
    }

    private static final Map<Integer, String> cacheKeysMap = Collections.synchronizedMap(new HashMap<Integer,String>());

    public static Observable<Data> getLoaderObservable(final ImageView img, final String url) {
        if (img != null) {
            cacheKeysMap.put(img.hashCode(), url);
        }
        Observable<Data> source = Observable.concat(mCacheManager.memory(url), mCacheManager.disk(url), mCacheManager.network(url))
                .first(new Func1<Data, Boolean>() {
                    @Override
                    public Boolean call(Data data) {
                        return data != null && data.isAvailable() && url.equals(data.url);
                    }
                });

        return source.doOnNext(new Action1<Data>() {
            @Override
            public void call(Data data) {
                if (img != null && url.equals(cacheKeysMap.get(img.hashCode()))) {
                    img.setImageBitmap(data.bitmap);
                }
            }
        });
    }
}
