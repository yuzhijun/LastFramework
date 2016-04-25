package com.medical.lenovo.lastframework.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.medical.lenovo.lastframework.network.HttpMethods;
import com.medical.lenovo.lastframework.network.ServiceFactory;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by lenovo on 2016/3/30.
 */
public abstract class BaseAcitivty extends AppCompatActivity {
    protected ServiceFactory mServiceFactory = HttpMethods.getInstance().getServiceFactory();
    protected CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeContentView();
        setContentView(getContentView());
        obtainParam(getIntent());
        initView();
        initListener();
        initData();
    }

    protected abstract void obtainParam(Intent intent);

    protected abstract void beforeContentView();

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }
}
