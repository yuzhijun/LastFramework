package com.medical.lenovo.lastframework.network;

import com.medical.lenovo.lastframework.exception.ApiException;
import com.medical.lenovo.lastframework.model.HttpResult;
import com.medical.lenovo.lastframework.model.Weather;
import com.medical.lenovo.lastframework.model.Weather2;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2016/3/29.
 */
public class HttpMethods {

    private static final String BASE_URL =  "http://ip.taobao.com/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private ServiceFactory serviceFactory;
    private ApiService apiService;
    private ApiGenericService apiGenericService;


    private static  HttpMethods httpMethods ;

    private HttpMethods(){
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        //.创建retrofit对象
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        apiGenericService = retrofit.create(ApiGenericService.class);
    }

    public static HttpMethods getInstance(){

        if (httpMethods == null){
            synchronized (HttpMethods.class){
                if (httpMethods == null){
                    httpMethods = new HttpMethods();
                }
            }
        }

        return httpMethods;
    }

    public ServiceFactory getServiceFactory(){
        if (serviceFactory == null){
            synchronized (ServiceFactory.class){
                if (serviceFactory == null){
                    serviceFactory = new ServiceFactory(retrofit);
                }
            }
        }
        return serviceFactory;
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCode() != 0) {
                throw new ApiException(httpResult.getCode());
            }
            return httpResult.getData();
        }
    }


    public void getWeather(Subscriber<Weather> subscriber,String IP){
        apiService.getWeatherResult2(IP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void getWeather2(Subscriber<HttpResult<Weather>> subscriber,String IP){
        apiService.getWeatherResult3(IP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getWeather3(Subscriber<Weather2> subscriber,String IP){
        apiService.getWeatherResult4(IP)
                .map(new HttpResultFunc<Weather2>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //方法不可行
    public <T> void getWeather4(Class<T> c,Subscriber<T> subscriber,String IP){
        apiGenericService.getWeatherResult5(c,IP)
                .map(new HttpResultFunc<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
