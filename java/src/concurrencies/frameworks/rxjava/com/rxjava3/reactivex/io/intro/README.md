# ReactiveX

ReactiveX is a library for composing[hợp thành-tổ hợp] asynchronous and event-based programs by using observable sequences.

It extends the observer pattern to support sequences of data and/or events and adds operators that allow you to compose sequences together declaratively while abstracting away concerns about things like low-level threading, synchronization, thread-safety, concurrent data structures, and non-blocking I/O.

Observables[có thể quan sát] fill the gap by being the ideal way to access asynchronous sequences of multiple items

||single items	|multiple items|
|-----|----|-----|
|synchronous	|T getData()	|Iterable<T> getData()|
|asynchronous	|Future<T> getData()	|Observable<T> getData()|

It is sometimes called “functional reactive programming” but this is a misnomer[sự nhầm tên]. ReactiveX may be functional, and it may be reactive, but “functional reactive programming” is a different animal. One main point of difference is that functional reactive programming operates on values that change continuously over time, while ReactiveX operates on discrete[riêng biệt] values that are emitted over time. (See Conal Elliott’s work for more-precise information on functional reactive programming.) 

## Why Use Observables?

The ReactiveX Observable model allows you to treat[luận giải] streams of asynchronous events with the same sort of simple, composable operations that you use for collections of data items like arrays. It frees you from tangled webs of callbacks, and thereby makes your code more readable and less prone to bugs.

### Observables Are Composable[Tổng hợp]

Techniques like Java Futures are straightforward to use for a single level of asynchronous execution but they start to add non-trivial complexity when they’re nested.

It is difficult to use Futures to optimally compose conditional asynchronous execution flows (or impossible, since latencies of each request vary at runtime). This can be done, of course, but it quickly becomes complicated (and thus error-prone) or it prematurely blocks on Future.get(), which eliminates the benefit of asynchronous execution.

ReactiveX Observables, on the other hand, are intended for composing flows and sequences of asynchronous data.

### Observables Are Flexible[Linh hoạt]

ReactiveX Observables support not just[chỉ] the emission of single scalar values (as Futures do), but also of sequences of values or even infinite streams. Observable is a single abstraction that can be used for any of these use cases. An Observable has all of the flexibility and elegance associated with its mirror-image cousin the Iterable. 

<b>An Observable is the asynchronous/push “dual” to the synchronous/pull Iterable</b>

|event	|Iterable (pull)	|Observable (push)|
|-------|-------------------|-----------------|
|retrieve data	|T next()	|onNext(T)|
|discover error	|throws Exception	|onError(Exception)|
|complete	|!hasNext()	|onCompleted()|

### Observables Are Less Opinionated

ReactiveX is not biased[chịu ảnh hưởng] toward some particular source of concurrency or asynchronicity. Observables can be implemented using thread-pools, event loops, non-blocking I/O, actors (such as from Akka), or whatever implementation suits your needs, your style, or your expertise. Client code treats all of its interactions with Observables as asynchronous, whether your underlying implementation is blocking or non-blocking and however you choose to implement it. 

#### How is this Observable implemented?
```java
public Observable<data> getData();
```
From the Observer’s point of view, it doesn’t matter!

* does it work synchronously on the same thread as the caller?
* does it work asynchronously on a distinct thread?
* does it divide its work over multiple threads that may return data to the caller in any order?
* does it use an Actor (or multiple Actors) instead of a thread pool?
* does it use NIO with an event-loop to do asynchronous network access?
* does it use an event-loop to separate the work thread from the callback thread?

#### From the Observer’s point of view, it doesn’t matter!
And importantly: with ReactiveX you can later change your mind, and radically change the underlying nature of your Observable implementation, without breaking the consumers of your Observable. 

### Callbacks Have Their Own Problems

Callbacks solve the problem of premature[sinh non] blocking on Future.get() by not allowing anything to block. They are naturally efficient because they execute when the response is ready.

But as with Futures, while callbacks are easy to use with a single level of asynchronous execution, with nested composition they become unwieldy.

### ReactiveX Is a Polyglot Implementation

ReactiveX is currently implemented in a variety of languages, in ways that respect those languages’ idioms, and more languages are being added at a rapid clip.

## Reactive Programming

ReactiveX provides a collection of operators with which you can filter, select, transform, combine, and compose Observables. This allows for efficient execution and composition.

You can think of the Observable class as a “push” equivalent to Iterable, which is a “pull.” With an Iterable, the consumer pulls values from the producer and the thread blocks until those values arrive. By contrast, with an Observable the producer pushes values to the consumer whenever values are available. This approach is more flexible, because values can arrive synchronously or asynchronously.

Example code showing how similar high-order functions can be applied to an Iterable and an Observable

|Iterable|	Observable|
|--------|------------|
|getDataFromLocalMemory()<br/>.skip(10)<br/>  .take(5)<br/>  .map({ s -> return s + " transformed" })<br/>  .forEach({ println "next => " + it })|getDataFromNetwork()<br/>  .skip(10)<br/>  .take(5)<br/>  .map({ s -> return s + " transformed" })<br/>  .subscribe({ println "onNext => " + it })|

The Observable type adds two missing semantics to the Gang of Four’s Observer pattern, to match those that are available in the Iterable type:

1. the ability for the producer to signal to the consumer that there is no more data available (a foreach loop on an Iterable completes and returns normally in such a case; an Observable calls its observer’s onCompleted method)
2. the ability for the producer to signal to the consumer that an error has occurred (an Iterable throws an exception if an error takes place during iteration; an Observable calls its observer’s onError method)

With these additions, ReactiveX harmonizes the Iterable and Observable types. The only difference between them is the direction in which the data flows. This is very important because now any operation you can perform on an Iterable, you can also perform on an Observable. 