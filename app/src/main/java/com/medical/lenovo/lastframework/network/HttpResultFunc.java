package com.medical.lenovo.lastframework.network;

import com.medical.lenovo.lastframework.exception.ApiException;
import com.medical.lenovo.lastframework.model.HttpResult;

import rx.functions.Func1;

/**
 * Created by lenovo on 2016/3/30.
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> httpResult) {
        if (httpResult.getCode() != 0) {
            throw new ApiException(httpResult.getCode());
        }
        return httpResult.getData();
    }
}