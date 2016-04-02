package com.medical.lenovo.lastframework.network;

import com.medical.lenovo.lastframework.base.BaseServiceFactory;
import com.medical.lenovo.lastframework.model.Weather2;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lenovo on 2016/3/30.
 *  一.过滤
 *      1.filter 过滤结果集中不想要的值
 *      2.take 获取整个序列中开头的几个序列
 *      3.takeLast 获取整个序列中结尾的几个序列
 *      4.distinct 可观测序列会在出错时重复发射或者被设计成重复发射,distinct是用来过滤这些重复问题
 *      5.distinctUntilsChanged 过滤重复发射的元素值
 *      6.first 从可观测源序列中创建只发射第一个元素的序列
 *      7.last 从可观测源序列中创建只发射最后一个元素的序列
 *      8.skip skip(2)来创建一个不发射前两个元素而是发射它后面的那些数据的序列
 *      9.skipLast 创建一个不发射后面N个元素而发射它前面的那些数据的序列
 *      10.elementAt 从一个序列中发射第n个元素然后就完成了
 *      11.sample 创建一个新的可观测序列，它将在一个指定的时间间隔里由Observable发射最近一次的数值
 *          sample(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())  sample(3,TimeUnit.SECONDS)
 *      12.timeout 在指定的时间间隔内Observable不发射值的话，它监听的原始的Observable时就会触发onError()函数
 *  二.变换
 *      1.map 函数只有一个参数，参数一般是Func1，Func1的<I,O>I,O模版分别为输入和输出值的类型，实现Func1的call方法对I类型进行处理后返回O类型数据
 *      2.flatMap 函数也只有一个参数，也是Func1,Func1的<I,O>I,O模版分别为输入和输出值的类型，实现Func1的call方法对I类型进行处理后返回O类型数据，
 *          不过这里O为Observable类型
 *          flatMap(new Func1<List<String>, Observable<String>>() {
 *                @Override
 *               public Observable<String> call(List<String> urls) {
 *              return Observable.from(urls);
 *           }
 *           })
 *      3.concatMap 作用与flatMap类似，只是flatMap会打乱顺序，而concatMap不会打乱顺序
 *      4.flatMapIterable flatMapIterable和flatMap基相同，不同之处为其转化的多个Observable是使用Iterable作为源数据的
 *          Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
 *                       .flatMapIterable(
 *                           integer -> {
 *                               ArrayList<Integer> s = new ArrayList<>();
 *                               for (int i = 0; i < integer; i++) {
 *                                   s.add(integer);
 *                               }
 *                               return s;
 *                       }
 *                       );
 *      5.switchMap 每当源Observable发射一个新的数据项（Observable）时，它将取消订阅并停止监视之前那个数据项产生的Observable，
 *          并开始监视当前发射的这一个,其他方面跟flatMap很像
 *      6.scan 对原始Observable发射的每一项数据都应用一个函数，计算出函数的结果值，并将该值填充回可观测序列，等待和下一次发射的数据一起使用
 *          example:创建一个新版本的loadList()函数用来比较每个安装应用的名字从而创建一个名字长度递增的列表。
 *          scan((appInfo,appInfo2) -> {
 *               if(appInfo.getName().length > appInfo2.getName().length()){
 *                   return appInfo;
 *               } else {
 *                   return appInfo2;
 *               }
 *           })
            .distinct()
 *      7.groupBy 将发射的值根据设定的条件来进行分组
 *          groupBy(new Func1<AppInfo,String>(){
 *               @Override
 *               public String call(AppInfo appInfo){
 *                   SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
 *                   return formatter.format(new Date(appInfo.getLastUpdateTime()));
 *               }
 *           });
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
