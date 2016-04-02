package com.medical.lenovo.lastframework.utils.RxImageLoader;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemoryCache<T> extends LruCache<T,Bitmap> {

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(T key, Bitmap bitmap) {
        int size = 0;
        if (bitmap != null) {
            size = bitmap.getRowBytes() * bitmap.getHeight();
        }
        return size;
    }
}
