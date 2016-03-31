package com.medical.lenovo.lastframework.base;

import android.support.v7.app.AppCompatActivity;

import com.medical.lenovo.lastframework.network.HttpMethods;
import com.medical.lenovo.lastframework.network.ServiceFactory;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by lenovo on 2016/3/30.
 */
public class BaseAcitivty extends AppCompatActivity {
    protected ServiceFactory mServiceFactory = HttpMethods.getInstance().getServiceFactory();
    protected CompositeSubscription mCompositeSubscription
            = new CompositeSubscription();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }
}
