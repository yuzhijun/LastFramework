package com.medical.lenovo.lastframework.network;

import com.medical.lenovo.lastframework.base.BaseServiceFactory;
import com.medical.lenovo.lastframework.model.Weather2;
import com.medical.lenovo.lastframework.network.ApiService;
import com.medical.lenovo.lastframework.network.HttpResultFunc;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lenovo on 2016/3/30.
 */
public class ServiceFactory extends BaseServiceFactory {
    private ApiService apiService;

    public ServiceFactory(Retrofit retrofit){
        apiService = retrofit.create(ApiService.class);
    }

    public void getWeather5(Subscriber<Weather2> subscriber,String IP){
        Observable<Weather2> observable = apiService.getWeatherResult4(IP)
                .map(new HttpResultFunc<Weather2>());

        subscribe(observable, subscriber);
    }

    public Subscription getWeather6(Subscriber<Weather2> subscriber,String IP){
        Observable<Weather2> observable = apiService.getWeatherResult4(IP)
                .map(new HttpResultFunc<Weather2>());

        return subscribe(observable, subscriber);
    }

}
