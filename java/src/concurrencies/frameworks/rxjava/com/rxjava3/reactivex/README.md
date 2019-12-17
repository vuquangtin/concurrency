# Creating Observables

@see https://github.com/ReactiveX/RxJava/wiki/Creating-Observables

## just

Available in: image Flowable, image Observable, image Maybe, image Single, image Completable

ReactiveX documentation: http://reactivex.io/documentation/operators/just.html

Constructs a reactive type by taking a pre-existing object and emitting that specific object to the downstream consumer upon subscription.

### just example:

```java
String greeting = "Hello world!";

Observable<String> observable = Observable.just(greeting);

observable.subscribe(item -> System.out.println(item));

```

There exist overloads with 2 to 9 arguments for convenience, which objects (with the same common type) will be emitted in the order they are specified.

```java
Observable<Object> observable = Observable.just("1", "A", "3.2", "def");

  observable.subscribe(item -> System.out.print(item), error -> error.printStackTrace(),
                () -> System.out.println());
```
## From
Constructs a sequence from a pre-existing source or generator type.

Note: These static methods use the postfix naming convention (i.e., the argument type is repeated in the method name) to avoid overload resolution ambiguities.

ReactiveX documentation: http://reactivex.io/documentation/operators/from.html

### fromIterable
Available in: image Flowable, image Observable, image Maybe, image Single, image Completable

Signals the items from a java.lang.Iterable source (such as Lists, Sets or Collections or custom Iterables) and then completes the sequence.

fromIterable example:

```java
List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

Observable<Integer> observable = Observable.fromIterable(list);

observable.subscribe(item -> System.out.println(item), error -> error.printStackTrace(), 
     () -> System.out.println("Done"));
```

### fromArray
Available in: image Flowable, image Observable, image Maybe, image Single, image Completable

Signals the elements of the given array and then completes the sequence.

fromArray example:

```java
Integer[] array = new Integer[10];
for (int i = 0; i < array.length; i++) {
    array[i] = i;
}

Observable<Integer> observable = Observable.fromArray(array);

observable.subscribe(item -> System.out.println(item), error -> error.printStackTrace(), 
     () -> System.out.println("Done"));
```

Note: RxJava does not support primitive arrays, only (generic) reference arrays.





