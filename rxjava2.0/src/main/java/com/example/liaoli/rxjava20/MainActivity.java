package com.example.liaoli.rxjava20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                e.onNext("1");
                e.onNext("2");
                e.onComplete();
            }
        });


        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                log("onSubscribe-->","Disposable :" +d.toString());
            }

            @Override
            public void onNext(String value) {
                log("onSubscribe-->","value :" +value);
            }

            @Override
            public void onError(Throwable e) {
                log("onError-->","");
            }

            @Override
            public void onComplete() {
                log("onComplete-->","");
            }
        };

      observable.subscribe(observer);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    public void log(String method, String content){

        Log.e("rxjava2.0",method + content);
    }
}
