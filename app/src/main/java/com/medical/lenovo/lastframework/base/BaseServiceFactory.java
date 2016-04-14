package com.medical.lenovo.lastframework.base;

import com.medical.lenovo.lastframework.network.OverWeightFunc;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class BaseServiceFactory {

    protected <T> Subscription subscribe(Observable<T> observable, Subscriber<T> subscriber){
        return observable.compose(this.<T>applySchedulers())
                .subscribe(subscriber);
    }

    public <T> Subscription getOverWeightDatas(Subscriber<T> subscriber,OverWeightFunc.IOverWeight<T> overWeight){
        OverWeightFunc<T> overWeightFunc = new OverWeightFunc<T>();
        overWeightFunc.registerIOverWeight(overWeight);
        return Observable.defer(overWeightFunc)
                .compose(this.<T>applySchedulers())
                .subscribe(subscriber);
    }

    final Observable.Transformer schedulersTransformer = new Observable.Transformer(){

        @Override
        public Object call(Object observable) {
            return ((Observable)observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }
}
