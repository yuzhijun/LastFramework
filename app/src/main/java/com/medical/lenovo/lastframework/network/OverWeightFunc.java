package com.medical.lenovo.lastframework.network;

import java.util.List;

import rx.Observable;
import rx.functions.Func0;

/**
 * Created by lenovo on 2016/3/31.
 */
public class OverWeightFunc<T> implements Func0<Observable<T>> {
    private List<T> mdatas;
    private IOverWeight<T> overWeight;

    @Override
    public Observable<T> call() {
        List<T> mdatas = overWeight.getOverWeightDatas();
        return Observable.from(mdatas);
    }

    public void registerIOverWeight(IOverWeight<T> overWeight){
        this.overWeight = overWeight;
    }

    public interface IOverWeight<E>{
        public List<E> getOverWeightDatas();
    }
}
