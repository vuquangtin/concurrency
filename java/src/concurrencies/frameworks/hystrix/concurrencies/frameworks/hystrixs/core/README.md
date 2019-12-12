# RxJava with Examples | Reactive Programming for Beginners 
Hey, guys today we’re going to learn about RxJava. RxJava stands for a Reactive extension. RxJava is one of the most popular libraries for reactive programming. Reactive programming basically provides a simple way of asynchronous programming. RxJava follows the Observer pattern.

People are gonna say’s you like asynchronous is complex, it’s very hard. All this true working with asynchronous is difficult but RxJava library gives you a very simple way to convert your task in to asynchronously very easily. You can achieve this thing with simple java but let me tell you this not an easy thing to do.

The first thing you know about RxJava is there are three constructs.

The first construct is Observable.

<b>Observable</b>:  
>Observable are the sources for data to emit. An observable start providing data once a subscriber or observer start listening. An Observable may emit n number of items. It will terminate with success or with an error. For example an object of User who emits User object with a new username, when the new username is set.

The second construct is Subscriber or Observer.

<b>Subscriber</b>: 
>Subscriber basically listens to those events emitted by observable. An observable may have any number of subscribers.

The third construct is Schedulers.

<b>Schedulers</b>: 
>Another super huge advantage with RxJava is Instance concurrency. The way RxJava does that is with Schedulers. For example, We say hey you have this observable and this observer when you established this connection, basically do it in this particular thread.

## Creating Observable

Various types of creating Observable.

<b>Single</b>: You subscribe to a single either you get back value or an error.

* Either succeeds with an item or error.
* No BackPressure support.
* Think “Reactive scalar”.
Completable: It is a set of code you can run then it may complete successfully or fail.

* Completable similar to a method that return type is void.

* Either completes or errors or has no items.

* No BackPressure support.

* Think “Reactive runnable”.

Maybe: This either has an item error or potentially has no items.

* No BackPressure support.

* Think “Reactive optional”.

Flowable: This either emits 0 item or emits n items terminates with success or with an error event.

* Have BackPressure support.

* Can emit any number of items.

Observable: This either emits 0 item or emits n items terminates with success or with an error event.

* No BackPressure Support.

* Can emit any number of items.

Observables emit table.

|Emit Source	|	Reactive Stream	|	No BackPressure
|-----------|:------------------:|-----------------:|
|o..n items,complete,error|	Flowable	|Observable	  |
|item,complete,error|		|Maybe|
|item,error		|			|Single
|complete,error	|			|Completable

So, enough of this theory, let’s dive into coding and see how we can create observables.

Before start please add RxJava dependency.

For Java

```maven
    <dependency>
        <groupId>io.reactivex.rxjava2</groupId>
        <artifactId>rxjava</artifactId>
        <version>2.0.4</version>
    </dependency>
```
The following shows an example how we can create simple observable.

```java
    Observable<String> helloWorldObservable = Observable.just("Hello World");
```

RxJava provides so many static methods for creating observables. Just is one of the static methods for creating observable. Just is basically saying give me the observable of hello string. You can pass any object in Just method instead of string to create observable. So, this our first construct for creating observable.

The following shows how we can subscribe to observable.

```java
    helloWorldObservable.subscribe(new DefaultObserver<String>() {
                @Override
                public void onNext(String s) {
                   System.out.println(s);
                }
                @Override
                public void onError(Throwable e) {
                }
                @Override
                public void onComplete() {
                }
           });
```

When we called subscribe on observable, observable start emitting item. You see subscribe method accepts Observer interface as a parameter. The onNext() method is called when observable emit new item. The onError() method is called when an error is occurred in emitting. The onComplete() method is called when observable finishes its data flow. So, this is our second construct.

The following show previous example with lambdas.

```java
    helloWorldObservable.subscribe(s -> System.out.println(s), throwable -> throwable.printStackTrace(), () -> System.out.println("Emittion completes"));
```

Let’s see another example of creating observable.

```java
    String strings[] = new String[]{"Hello", "World"};     // Array of resource
    Observable.fromArray(strings).subscribe(s -> System.out.println(s),throwable -> throwable.printStackTrace(),() -> System.out.println("Emittion completed"));  
```

FromArray is another static method for creating observable. The  FromArray method takes the array of objects and returns the array of object of observable. Now every time onNext() method called, it received a single string value from the array.

The following example shows how you can apply logic before actually receiving the stream.

```java
    List<Integer> intergerList = Arrays.asList(54,12,10,78,69,33,66,99,84);
    Observable.fromIterable(intergerList)
              .filter(i -> i % 2 == 0)
              .sorted()
              .subscribe(i -> System.out.println(i), throwable -> throwable.printStackTrace(), () -> System.out.println("Emittion completes"));
```
```java
    // Output
    10
    12
    54
    66
    78
    84
```

FromIterable is another static method for creating observable. The FromIterable method takes the list of objects as a parameter and returns the list of object of observable. Another thing of noticeable here is the chaining of observables. You see every operator returns an observable and we can chain them. The filter method takes the Predicate interface and performs the logic on it. The sorted method sorts the result in ascending order. You can also pass the custom Comparator interface for sorting.

The following example shows how you can merge the result of two observable into one. Another cool feature of a startWith method.

```java
    static class Person {
            enum SEX {
                MALE, FEMALE, SHE_MALE
            }
            private String name;
            private int age;
            SEX sex;
            Person(String name, int age, SEX sex) {
                this.name = name;
                this.age = age;
                this.sex = sex;
            }
    }    This Person class we are going to use in our example.
    // Create observable code.
    Observable<Person> firstObservable = Observable.create(emitter -> emitter.onNext(new Person("Shayan Tahir", 22, Person.SEX.MALE)));
    Observable<Person> secondObservable = Observable.create(emitter -> emitter.onNext(new Person("Bilal Ahmed", 25, Person.SEX.MALE)));
    secondObservable.mergeWith(firstObservable)
                    .startWith(new Person("Jon Doe", 20, Person.SEX.FEMALE))
                    .subscribe(person -> System.out.println(person.name)
                            , Throwable::printStackTrace,() -> System.out.println("Emittion complete"));
    // Output
    // Jon Doe
    // Bilal Ahmed
    // Shayan Tahir
```

Create is another static method for creating observable. The Create method accepts ObservableOnSubscribe interface for creating observable. With Create method we have the ability to call onNext multiple times. Now with merge method, we can merge the output of two observable into one. Another interesting method is startWith. The startWith method returns an Observable that emits a specified item before emitting the sources.

RxJava provides several other methods for creating observable.

* Observable.fromCallable(): FromCallable essentially modeling some synchronous behavior that returns a single value.

* Observable.timer(): This function means to execute this task every x seconds until someone unsubscribes it.

* Observable.interval(): Execute this function every x seconds without delay and stops when someone unsubscribes it.

* Observable.concat(): Concatenates the elements of each observable provided by the observables as a parameter.

>Note: Similar methods exist in all observable types. Examples Flowable, Maybe, Completeable and Single.

Now, you guy’s must be thinking where is the asynchronous code, how we can handle multithreading with this. Let me tell you what we do before these all are the basics of RxJava how to create observables. Now we’re going to see the real power of RxJava.

The following is the example of how we can create a timer task with observable.

```java
    Disposable disposable = Observable.timer(5000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .subscribe(aLong -> {
                                  doLongRunningTask();
                            }
                            , Throwable::printStackTrace,
                            () -> System.out.println("Observable complete"));
```

This piece of code runs after every five seconds and do some long running task. SubscribeOn is the method in which we tell do the task to run in a background thread by giving Schedulers object.

The following example shows how we can make a network request with observable.

```java
    Observable<User> userObservable = Observable.fromCallable(() -> client.getUser(request.execute()));
```
This is how you can make a network request with observables. The client is the network interface. But we have a problem here, that network request still going to be done synchronously. So, what do we do to make it asynchronous?

The following example shows how we can make network request asynchronously with observable.

```java
    Observable.fromCallable(() -> client.getUser(request.execute()))
                    .subscribeOn(Schedulers.io())
                    .subscribe(user -> {
                        // performOperationWithUser(user);
                    });
```

So, we apply an operator that changes the thread with background thread when we subscribe to the observable. The subscribeOn is the operator that changes the current thread with a background thread and accepts a Schedulers object as a parameter.

The following example shows how you can make two asynchronous network request with dependency. The second request depends on first request response.

```java
    Observable<User> tempObservable = Observable.fromCallable(() -> client.getUser(request.execute()))
            .subscribeOn(Schedulers.io());
    tempObservable
            .map(user -> user.getId())
            .flatMap(id -> Observable.fromCallable(() ->
                    client.getUserSettings(request.execute(id)))
                    .subscribeOn(Schedulers.io()))
            .subscribe(userSettings -> {
                   showUserSettings()
            }, throwable -> throwable.printStackTrace(), () -> System.out.println("Request completes"));
```

At first, we simply make a getUser network request. With first observable when we get a User object, we make another request for fetching UserSettings request. In the previous example, we have a map and flatMap operator, map operator provides you a function that it basically returns a different object and flatMap operator basically accepts an object and return a new observable. Finally, we get the userSettings object in subscribe method.

Below example shows how you can make two network request that is independent of each other.

```java
    Observable.merge(Observable.fromCallable(() -> client.updateUser(request.execute(user)))
                    .subscribeOn(Schedulers.io()),
            Observable.fromCallable(() -> client.addNewUserComment(request.execute(user.getId)))
                    .subscribeOn(Schedulers.io()))
            .subscribe(response -> System.out.println("Data inserted in to database.")
                    , Throwable::printStackTrace);
```

Observable.merge is the static method for creating observable. In here it basically executes two network request simultaneously, when both request complete with success it will be called onNext, if it got an error it will be called the onError method.

RxJava provides many methods for converting the observable into other types.

## Conversion of Observable.

|From / To	|Flowable|	Observable|	Maybe	|Single	|Completeable|
|------------|--------|------------|----------|-------|------------|
|Flowable	||	toObservable()|	elementAt()<br/> reduce()<br/>firstElement()<br>lastElement()<br/>singleElement()|	scan()<br>elementAt()<br>first()<br>firstOrError()<br>single()<br>singleOrError()<br>last()<br>lastOrError()<br>all()<br>any()<br>count()	|ignoreElements()|
|Observable	|toFlowable()		||elementAt()<br>reduce()<br>firstElement()<br>lastElement()<br>singleElement()|	scan()<br>elementAt()<br>first()<br>firstOrError()<br>single()<br>singleOrError()<br>last()<br>lastOrError()<br>all()<br>any()	|ignoreElements()
|Maybe	|toFlowable()	|toObservable()		|toSingle()|seruenceEqual()	|toCompleteable()
|Single	|toFlowable()	|toObservable()	|toMaybe()		||toCompleteable()
|Completeable	|toFlowable()|	toObservable()	|toMaybe()	|toSingle()|toSingleDefault()	

Now it’s time to see how RxJava helps us to use this in the making of Android App. For this, we have to add another dependency to our build.gradle file.

```java
    // Rx java dependency
    implementation 'io.reactivex.rxjava2:rxjava:2.1.8'
    // Rx Android dependency
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'          // change version number with latest version.
```

@see <a href="https://codinginfinite.com/rxjava-examples-reactive-programming-beginners/">https://codinginfinite.com/rxjava-examples-reactive-programming-beginners/</a>