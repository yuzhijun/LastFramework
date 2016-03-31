package com.medical.lenovo.lastframework.network;

import com.medical.lenovo.lastframework.model.HttpResult;
import com.medical.lenovo.lastframework.model.Weather2;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lenovo on 2016/3/29.
 */
public interface ApiGenericService {

    @GET("service/getIpInfo.php")
    <T> Observable<HttpResult<T>> getWeatherResult5(Class<T> c,@Query("ip") String ip);

//    @GET("service/getIpInfo.php")
//    Observable<HttpResult<Weather2>> getWeatherResult5(@Query("ip") String ip);
}
