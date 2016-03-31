package com.medical.lenovo.lastframework.network;

/**
 * Created by lenovo on 2016/3/30.
 */
public interface SubscriberListener<T>{
    void onNext(T t);
    void onComplete();
    void onError(Throwable e);
    void preStart();
}
