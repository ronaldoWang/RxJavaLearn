package com.rx.learn;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ronaldo on 2017/5/29.
 */
public class RxTest1 {
    public static void main(String[] args) {
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("hello RxJava 2");
                //e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };


        flowable.subscribe(subscriber);

        Flowable.just("hello RxJava 2")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });

        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(1);
        list.add(5);

        Flowable.fromIterable(list).subscribe(num -> System.out.println(num));

        Flowable.fromArray(list).subscribe(num -> System.out.println(num));

        Flowable.fromArray(1, 20, 5, 0, -1, 8)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer.intValue() > 5;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });

    }
}
