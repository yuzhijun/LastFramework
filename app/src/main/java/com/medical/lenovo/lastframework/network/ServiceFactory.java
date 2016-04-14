package com.medical.lenovo.lastframework.network;

import com.medical.lenovo.lastframework.base.BaseServiceFactory;
import com.medical.lenovo.lastframework.model.Weather2;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by lenovo on 2016/3/30.
 *  o.创建Observable操作符
 *      1.Create — 通过调用观察者的方法从头创建一个Observable
 *      2.Defer — 在观察者订阅之前不创建这个Observable，为每一个观察者创建一个新的Observable
 *      3.Interval — 创建一个定时发射整数序列的Observable
 *      4.Just — 将对象或者对象集合转换为一个会发射这些对象的Observable
 *      5.Range — 创建发射指定范围的整数序列的Observable
 *      6.Repeat — 创建重复发射特定的数据或数据序列的Observable
 *      7.Timer — 创建在一个指定的延迟之后发射单个数据的Observable
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
 *      8.buffer 将源Observable变换一个新的Observable，这个新的Observable每次发射一组列表值而不是一个一个发射
 *      9.window 和buffer()很像，但是它发射的是Observable而不是列表
 *      10.cast 它是map()操作符的特殊版本。它将源Observable中的每一项数据都转换为新的类型，把它变成了不同的Class
 *  三.组合
 *      1.merge merge()方法将帮助你把两个甚至更多的Observables合并到他们发射的数据项里
 *      2.zip 多从个Observables接收数据，处理它们，然后将它们合并成一个新的可观测序列来使用
 *      3.join 函数基于时间窗口将两个Observables发射的数据结合在一起
 *      4.combineLatest combineLatest()作用于最近发射的数据项：如果Observable1发射了A并且Observable2发射了B和C，
 *          combineLatest()将会分组处理AB和AC
 *      5.and,then和when 还有一些zip()满足不了的场景。如复杂的架构，或者是仅仅为了个人爱好，你可以使用And/Then/When解决方案
 *      6.concat 依次将多个数据源释放到同一个地方
 *          example:
 *          Observable.concat(Observable.just("a"), Observable.just("b"), Observable.just("c")).subscribe(
 *               new Observer<String>() {
 *                   @Override public void onCompleted() {
 *                       Log.e("test---onCompleted ", "onCompleted");
 *                   }
 *                   @Override public void onError(Throwable e) {
 *                   }
 *                   @Override public void onNext(String s) {
 *                       Log.e("test---onNext ", "onNext s : " + s);
 *                   }
 *                   });
 *      7.switch 将一个发射多个Observables的Observable转换成另一个单独的Observable，后者发射那些Observables最近发射的数据项。
 *      8.startWith 正如concat()向发射数据的Observable追加数据那样，在Observable开始发射他们的数据之前，
 *          startWith()通过传递一个参数来先发射一个数据序列。
 *  四.辅助函数
 *      1.repeat 假如你想对一个Observable重复发射三次数据 repeat(3)
 *      2.delay 延时数据的发射 delaySubscription 延时注册Subscriber
 *      3.all 根据一个函数对源Observable发射的所有数据进行判断，最终返回的结果就是这个判断结果
 *      4.amb 可以将至多9个Observable结合起来，让他们竞争。哪个Observable首先发射了数据（包括onError和onComplete)就会继续发射这个Observable的数据，
 *          其他的Observable所发射的数据都会别丢弃。
 *      5.contains 用来判断源Observable所发射的数据是否包含某一个数据，如果包含会返回true，如果源Observable已经结束了却还没有发射这个数据则返回false
 *      6.isEmpty 用来判断源Observable是否发射过数据，如果发射过就会返回false，如果源Observable已经结束了却还没有发射这个数据则返回true。
 *      7.defaultIfEmpty 判断源Observable是否发射数据，如果源Observable发射了数据则正常发射这些数据，如果没有则发射一个默认的数据
 *      8.sequenceEqual 判断两个Observable发射的数据序列是否相同（发射的数据相同，数据的序列相同，结束的状态相同），如果相同返回true，否则返回false
 *      9.reduce 操作符应用一个函数接收Observable发射的数据和函数的计算结果作为下次计算的参数，输出最后的结果。
 *
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
