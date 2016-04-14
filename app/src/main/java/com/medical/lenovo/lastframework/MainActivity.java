package com.medical.lenovo.lastframework;

import android.os.Bundle;
import android.widget.TextView;

import com.medical.lenovo.lastframework.base.BaseAcitivty;
import com.medical.lenovo.lastframework.base.BaseSubscriber;
import com.medical.lenovo.lastframework.event.UserEvent;
import com.medical.lenovo.lastframework.eventbus.RxBus;
import com.medical.lenovo.lastframework.eventbus.RxBusEx;
import com.medical.lenovo.lastframework.model.HttpResult;
import com.medical.lenovo.lastframework.model.Weather;
import com.medical.lenovo.lastframework.model.Weather2;
import com.medical.lenovo.lastframework.network.ApiService;
import com.medical.lenovo.lastframework.network.HttpMethods;
import com.medical.lenovo.lastframework.network.OverWeightFunc;
import com.medical.lenovo.lastframework.network.SubscriberListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseAcitivty implements SubscriberListener {


    private static final String BASE_URL = "http://ip.taobao.com/";
    private static final String IP = "63.223.108.42";
    @Bind(R.id.tvShow)
    TextView tvShow;
    //    private TextView tvShow;
    private Observable<String> observable;
    private Subscription rxSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        _initView();

//        queryWeather8();

//        testEventBus();
//        RxBusEx.getInstance().post("tag1", "hello rb!");
//        testEventBus1();
//        RxBus.getDefault().post(new UserEvent(1, "yoyo"));
        testOverweight();
    }

    public void _initView() {

//        tvShow = (TextView)findViewById(R.id.tvShow);
    }

    public void queryWeather() {
        //.创建retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        //2.创建访问api请求
        ApiService weatherService = retrofit.create(ApiService.class);
        Call<Weather> call = weatherService.getWeatherResult(IP);

        //3.发送请求
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                //4.处理结果
                if (response.isSuccess()) {
                    Weather weather = response.body();
                    if (weather != null) {
                        int code = weather.getCode();
                        tvShow.setText(code + "");
                    }
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });

    }


    public void queryWeather2() {

        //.创建retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //2.创建访问api请求
        ApiService weatherService = retrofit.create(ApiService.class);

        //3.请求网络
        weatherService.getWeatherResult2(IP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Weather weather) {
                        int code = weather.getCode();
                        tvShow.setText(code + "");
                    }
                });
    }


    public void queryWeather3() {
        Subscriber<Weather> subscriber = new Subscriber<Weather>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Weather weather) {
                int code = weather.getCode();
                tvShow.setText(code + "");
            }
        };

        HttpMethods.getInstance().getWeather(subscriber, IP);
    }

    public void queryWeather4() {

        Subscriber<HttpResult<Weather>> subscriber = new Subscriber<HttpResult<Weather>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpResult<Weather> weatherHttpResult) {
                int code = weatherHttpResult.getCode();
                tvShow.setText(code + "");
            }
        };

        HttpMethods.getInstance().getWeather2(subscriber, IP);
    }

    public void queryWeather5() {

        Subscriber<Weather2> subscriber = new Subscriber<Weather2>() {
            @Override
            public void onCompleted() {
                tvShow.setText("complete");
            }

            @Override
            public void onError(Throwable e) {
                tvShow.setText(e.getMessage());
            }

            @Override
            public void onNext(Weather2 weather) {
                String code = weather.getIp();
                tvShow.setText("hhhhh");
            }
        };

        HttpMethods.getInstance().getWeather3(subscriber, IP);
    }

    //此方法不可行
    public void queryWeather6() {

        Subscriber<Weather2> subscriber = new Subscriber<Weather2>() {
            @Override
            public void onCompleted() {
                tvShow.setText("complete");
            }

            @Override
            public void onError(Throwable e) {
                tvShow.setText(e.getMessage());
            }

            @Override
            public void onNext(Weather2 weather) {
                String code = weather.getIp();
                tvShow.setText("hhhhh");
            }
        };

        HttpMethods.getInstance().getWeather4(Weather2.class, subscriber, IP);
    }

    //
    public void queryWeather7() {
        HttpMethods.getInstance().getWeather3(new BaseSubscriber<Weather2>(this, 1), IP);
    }

    @Override
    public void onNext(Object o, int flag) {
//        if (o instanceof Weather2){
//            tvShow.setText("hhhhhdddddddddd");
//        }
        switch (flag) {
            case 1:
                break;
            default:
                break;
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void preStart() {

    }

    public void queryWeather8() {
        HttpMethods.getInstance().getServiceFactory().getWeather5(new BaseSubscriber<Weather2>(this, 1), IP);
    }

    public void queryWeather9() {

        Subscription subscription = HttpMethods.getInstance().getServiceFactory().getWeather6(new BaseSubscriber<Weather2>(this, 1), IP);
        mCompositeSubscription.add(subscription);
    }

    //--------------------------------eventbus------------------------------------


    public void testEventBus() {
        Observable<String> observable = RxBusEx.getInstance().register("tag1", String.class);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        tvShow.setText(s);
                    }
                });
    }


    public void testEventBus1() {

        // rxSubscription是一个Subscription的全局变量，这段代码可以在onCreate/onStart等生命周期内
        rxSubscription = RxBus.getDefault().toObserverable(UserEvent.class)
                .subscribe(new Action1<UserEvent>() {
                               @Override
                               public void call(UserEvent userEvent)

                               {
                                   tvShow.setText(userEvent.getName());
                               }
                           },
                        new Action1<Throwable>() {
                            @Override

                            public void call(Throwable throwable) {
                                // TODO: 处理异常
                            }
                        });
        mCompositeSubscription.add(rxSubscription);
    }

    //------------------------------耗时任务-----------------------------------------
    public void testOverweight() {
        Subscriber<Weather2> subscriber = new Subscriber<Weather2>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                tvShow.setText(e.getMessage());
            }

            @Override
            public void onNext(Weather2 weather) {
                String code = weather.getArea();
                tvShow.setText(code);
            }
        };

        mServiceFactory.getOverWeightDatas(subscriber, new OverWeightFunc.IOverWeight<Weather2>() {
            @Override
            public List<Weather2> getOverWeightDatas() {
                List<Weather2> weather2s = new ArrayList<Weather2>();
                Weather2 weather2 = new Weather2();
                weather2.setArea("dddd");
                weather2s.add(weather2);
                return weather2s;
            }
        });
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        RxBusEx.getInstance().unregister("tag1", observable);
//    }
}
