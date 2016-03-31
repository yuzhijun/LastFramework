package com.medical.lenovo.lastframework.network;


import com.medical.lenovo.lastframework.model.HttpResult;
import com.medical.lenovo.lastframework.model.Weather;
import com.medical.lenovo.lastframework.model.Weather2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lenovo on 2016/3/29.
 */
public interface ApiService {
    @GET("service/getIpInfo.php")
    Call<Weather> getWeatherResult(@Query("ip") String ip);

    @GET("service/getIpInfo.php")
    Observable<Weather> getWeatherResult2(@Query("ip") String ip);

    @GET("service/getIpInfo.php")
    Observable<HttpResult<Weather>> getWeatherResult3(@Query("ip") String ip);

    @GET("service/getIpInfo.php")
    Observable<HttpResult<Weather2>> getWeatherResult4(@Query("ip") String ip);

}
