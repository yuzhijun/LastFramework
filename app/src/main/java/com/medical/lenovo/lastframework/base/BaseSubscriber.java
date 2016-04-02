package com.medical.lenovo.lastframework.base;

import com.medical.lenovo.lastframework.network.SubscriberListener;

import rx.Subscriber;

/**
 * Created by lenovo on 2016/3/30.
 */
public class BaseSubscriber<T> extends Subscriber<T> {
    private SubscriberListener<T> subscriberOnNextListener;
    private int flag = -1;

    public BaseSubscriber(SubscriberListener<T> subscriberOnNextListener,int flag){
        this.subscriberOnNextListener = subscriberOnNextListener;
        this.flag = flag;
    }

    @Override
    public void onStart() {
        subscriberOnNextListener.preStart();
    }

    @Override
    public void onCompleted() {

        subscriberOnNextListener.onComplete();
    }

    @Override
    public void onError(Throwable e) {
        subscriberOnNextListener.onError(e);
    }

    @Override
    public void onNext(T t) {
            subscriberOnNextListener.onNext(t,flag);
    }
}
