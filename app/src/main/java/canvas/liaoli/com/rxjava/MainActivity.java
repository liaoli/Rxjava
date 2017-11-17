package canvas.liaoli.com.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;


public class MainActivity extends AppCompatActivity {

    private String TAG = "rxjava";
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);




//
//        AsyncSubject(asyncSubject);


//        behaviorSubject();

//        PublishSubject();


        ReplaySubject();
    }

    private void ReplaySubject() {
//        ReplaySubject<String> replaySubject = ReplaySubject.create();
//        ReplaySubject<String> replaySubject = ReplaySubject.create(100);

//        ReplaySubject<String> replaySubject = ReplaySubject.createWithSize(2);
        ReplaySubject<String> replaySubject = ReplaySubject.createWithTimeAndSize(2, TimeUnit.SECONDS,2,Schedulers.io());

        replaySubject.onNext("1");
        replaySubject.onNext("2");
        replaySubject.onNext("3");
        replaySubject.onNext("4");
        replaySubject.onNext("5");

        replaySubject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG,"onNext : " + s);
            }
        });

        replaySubject.onNext("6");
        replaySubject.onNext("7");
        replaySubject.onNext("8");

    }

    /**
     *PublishSubject的Observer只会接收到PublishSubject被订阅之后发送的数据
     *
     */
    private void PublishSubject() {
        PublishSubject<String> subject = PublishSubject.create();

        subject.onNext("1");
        subject.onNext("2");
        subject.onNext("3");
        subject.onNext("4");

        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,"onNext : " + s);
            }
        });

        subject.onNext("5");
        subject.onNext("6");

    }

    /**
     *behaviorSubject只接受订阅前发送的最后一个数据，之后还会会再接收后续发送的数据
     * 而BehaviorSubject不需手动调用onCompleted()
     */
    private void behaviorSubject() {
        BehaviorSubject<String> subject = BehaviorSubject.create();

        subject.onNext("1");
        subject.onNext("2");
        subject.onNext("3");
        subject.onNext("4");

        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,"onNext : " + s);
            }
        });

        subject.onNext("5");
        subject.onNext("6");

    }

    /**
     * 只接收AsyncSubject.onCompleted() 方法之之后一次的数据,不会接收后续数据
     */
    private void AsyncSubject() {
        AsyncSubject<String> subject = AsyncSubject.create();

        subject.onNext("1");
        subject.onNext("2");
        subject.onNext("3");
        subject.onNext("4");
        subject.onCompleted();

        subject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,"onNext : " + s);
            }
        });

        subject.onNext("5");
        subject.onNext("6");

    }

    private void operator() {

        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber){
                Log.e(TAG,"subscribe" + ",CurrentThread : " + Thread.currentThread() + "," +this);
                subscriber.onNext("haha ");
            }
        });


        Observer<String> observer = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,"onNext :" + s + ",CurrentThread : " + Thread.currentThread());
            }
        };

       // observable.subscribe(observer);



        Observable<String> repeat = observable.repeat();
        repeat.subscribe(observer);

//        Observable.just("haha").repeat(3).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.e(TAG,"onNext :" + s + ",CurrentThread : " + Thread.currentThread());
//            }
//        });
    }

    private void standard() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.e(TAG,"subscribe" + ",CurrentThread : " + Thread.currentThread() + "," +this);
                subscriber.onNext("haha ");
            }
        });


        Observer<String> observer = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,"onNext :" + s + ",CurrentThread : " + Thread.currentThread());
            }
        };

        observable.subscribe(observer);


        Observable<String> observable1 =   observable.subscribeOn(Schedulers.io());


        Observable<String> observable2 =   observable1.observeOn(Schedulers.newThread());




        subscription = observable1.subscribe(observer);

        observable2.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG,"call :" + s + ",CurrentThread : " + Thread.currentThread());
            }
        });
    }

    /**
     * Rxjava just 与from的区别
     */
    private void justAndFrom() {
        Observable<String> just = Observable.just("I","and","you");

        just.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG,"just call :" + s + ",CurrentThread : " + Thread.currentThread());
            }
        });


        ArrayList<String> list = new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add("3");


        Observable<String> from = Observable.from(list);

        from.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG,"from call :" + s + ",CurrentThread : " + Thread.currentThread());
            }
        });


        Observable<ArrayList<String>> just2 = Observable.just(list);

        just2.subscribe(new Action1<ArrayList<String>>() {
            @Override
            public void call(ArrayList<String> s) {
                Log.e(TAG,"just2 call :" + s + ",CurrentThread : " + Thread.currentThread());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription != null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
