package com.medical.lenovo.lastframework.utils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class RxUtil {
    //发送网络连接广播
//    public <T> Subscription sendConnectBroadCast(Context context, Subscriber<T> subscriber){
//        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//
//        return AndroidObservable.fromBroadcast(context, intentFilter)
//                .subscribe(subscriber);
//    }

    public IDelayTask delayTask;
    public interface IDelayTask{
        public void doDelayTask();
    }

    //延迟两秒执行
    public void doDelayTask(int Seconds,final IDelayTask delayTask) {
        this.delayTask = delayTask;
        Observable.timer(Seconds, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).map(new Func1<Long, Object>() {
            @Override
            public Object call(Long aLong) {
                delayTask.doDelayTask();
                return null;
            }
        }).subscribe();
    }


}
