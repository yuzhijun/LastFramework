package com.medical.lenovo.lastframework.interfaces;

/**
 * Created by yuzhijun on 2016/4/25.
 */
public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);
    int getItemViewType(int position, T t);
}
