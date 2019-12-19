package com.rxjava3.reactivex.io.operators;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class FilterExample implements MarbleDiagram {

    @Override
    public void marbleDiagram() {
        String[] objs = {"1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON"};
        Observable<String> source = Observable.fromArray(objs)
                .filter(obj -> obj.endsWith("CIRCLE"));
        source.subscribe(System.out::println);
    }

    public void evenNumbers() {
        Integer[] data = {100, 34, 27, 99, 50};
        Observable<Integer> source = Observable.fromArray(data)
                .filter(number -> number % 2 == 0);
        source.subscribe(System.out::println);
    }

    public void otherFilter() {
        Integer[] numbers = {100, 200, 300, 400, 500};
        Single<Integer> single;
        Observable<Integer> source;

        // 1. first
        single = Observable.fromArray(numbers).first(-1);
        single.subscribe(data -> System.out.println("first() value = " + data));

        // 1-1. test take(1)
        //single = Observable.fromArray(numbers).take(1);
        // error type mismatch

        // 2. last
        single = Observable.fromArray(numbers).last(999);
        single.subscribe(data -> System.out.println("last() value = " + data));

        // 2-1. test takeLast(1)
        //single = Observable.fromArray(numbers).takeLast(1);
        // error type mismatch

        // 3. take(N)
        source = Observable.fromArray(numbers).take(3);
        source.subscribe(data -> System.out.println("take(3) value = " + data));

        // 4. takeLast(N)
        source = Observable.fromArray(numbers).takeLast(3);
        source.subscribe(data -> System.out.println("takeLast(3) value = " + data));

        // 5. skip(N)
        source = Observable.fromArray(numbers).skip(2);
        source.subscribe(data -> System.out.println("skip(2) value = " + data));

        // 6. skipLast(N)
        source = Observable.fromArray(numbers).skipLast(2);
        source.subscribe(data -> System.out.println("skipLAst(2) value = " + data));
    }


    public static void main(String[] args) {
        FilterExample demo = new FilterExample();
        demo.marbleDiagram();
        demo.evenNumbers();
        demo.otherFilter();
    }
}
