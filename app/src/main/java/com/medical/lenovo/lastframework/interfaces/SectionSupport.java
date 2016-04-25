package com.medical.lenovo.lastframework.interfaces;

/**
 * Created by yuzhijun on 2016/4/25.
 */
public interface SectionSupport<T> {
    public int sectionHeaderLayoutId();
    public int sectionTitleTextViewId();
    public String getTitle(T t);
}
