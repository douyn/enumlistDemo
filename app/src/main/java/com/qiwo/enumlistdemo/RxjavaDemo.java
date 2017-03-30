package com.qiwo.enumlistdemo;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ImageView;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/10/6.
 */

public class RxjavaDemo extends BaseActivity{

    Observable<String> observable;
    Subscriber subscriber;

    @Override
    int getLayoutId() {
        return R.layout.activity_rxjavademo;
    }

    public void createObservable(){
    // 1 observable 被观察者：
        // 1.1 创建的方式有三种
        String[] attrs = {"1", "2", "3"};
        // ① create() 方法来创建一个 Observable ，并为它定义事件触发规则
        observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onNext("3");
                subscriber.onCompleted();
            }
        });

        // ② just(T...): 将传入的参数依次发送出来。
        Observable.just(attrs);

        // ③ from(T[]): 将传入的数组或者iterable转换成具体对象再发送出来
        Observable.from(attrs);
    }

    // 2 observer 观察者，它决定事件触发时有怎样的行为
    // 2.1 Subscriber是Observer的抽象类，对Observer进行了扩展，增加了onstart()事件被订阅之前调用和unsubscribe()取消订阅;
    public void createSubscriber(){
         subscriber = new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                subscriber.unsubscribe();
            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onStart() {
                super.onStart();
            }
        };
    }

    // 3 subscribe 订阅，创建了observerable和subscribe之后，再用subscribe将他们联系起来
    public void subscribe(){
        observable.subscribe(subscriber);

        // 3.1  注意：这不是 subscribe() 的源码，而是将源码中与性能、兼容性、扩展性有关的代码剔除后的核心代码。
        //      如果需要看源码，可以去 RxJava 的 GitHub 仓库下载。
        //      public Subscription subscribe(Subscriber subscriber) {
        //          subscriber.onStart();
        //          onSubscribe.call(subscriber);
        //          return subscriber;
        //      }

        // 3.2 subscribe() 还支持不完整定义的回调，RxJava 会自动根据定义创建出 Subscriber
        Action1 onNextAction = new Action1() {
            @Override
            public void call(Object o) {

            }
        };

        Action1 onErrorAction = new Action1() {

            @Override
            public void call(Object o) {

            }
        };

        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {

            }
        };

        // 自动创建subscriber，并且使用onnextaction定义onNext();
        observable.subscribe(onNextAction);
        // 自动 创建subscriber， 并且使用onnextaction和onerroraction定义onNext()和onError();
        observable.subscribe(onNextAction, onErrorAction);
        // 自动 创建subscriber， 并且使用onnextaction和onerroraction和oncompleteaction定义onNext()和onError()和onComplete)();
        observable.subscribe(onNextAction, onErrorAction, onCompleteAction);

        // 3.3 Action0和Action1
        // Action0 是 RxJava 的一个接口，它只有一个方法 call()，这个方法是无参无返回值的
        // Action1 是 RxJava 的一个接口，它只有一个方法 call(T params)，这个方法有一个参数
    }

    public void demo1(){
        // a. 打印字符串数组
        // 将字符串数组 names 中的所有字符串依次打印出来：
        String[] names = {"n", "a", "m", "e", "s"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("log", s);
                    }
                });
    }

    public void demo2(){
        //  b. 由 id 取得图片并显示
        //  由指定的一个 drawable 文件 id drawableRes 取得图片，并显示在 ImageView 中，并在出现异常的时候打印 Toast 报错：
        final int drawableres = R.drawable.icon_bb;
        
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(drawableres);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Drawable drawable) {
                ImageView iv = null;
                iv.setImageDrawable(drawable);
            }
        });
    }

    // 4 线程控制 Scheduler scheduler--调度器，相当于线程控制器，指定每一段代码运行在哪一个线程
        //    Rxjava已经内置了几个Scheduler：
        //    Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
        //    Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
        //    Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
        //    Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
        //    另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。

    // 4.1 Rxjava是通过SubscribeOn()和ObserveOn()来对线程进行控制的
    // SubscribeOn()指定subscribe所处的线程，即Observerable.onsubscribe被激活的线程，或者叫做事件产生的线程。
    // ObserveOn()指定Subscriber所运行的线程，或者叫做事件运行的线程。

}
