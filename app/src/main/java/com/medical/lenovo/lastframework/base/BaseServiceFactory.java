package com.medical.lenovo.lastframework.base;

import com.medical.lenovo.lastframework.network.OverWeightFunc;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2016/3/31.
 */
public class BaseServiceFactory {

    protected <T> Subscription subscribe(Observable<T> observable, Subscriber<T> subscriber){
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public <T> Subscription getOverWeightDatas(Subscriber<T> subscriber,OverWeightFunc.IOverWeight<T> overWeight){
        OverWeightFunc<T> overWeightFunc = new OverWeightFunc<T>();
        overWeightFunc.registerIOverWeight(overWeight);
        return Observable.defer(overWeightFunc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
