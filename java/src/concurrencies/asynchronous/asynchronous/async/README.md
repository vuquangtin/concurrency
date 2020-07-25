# Java CompletableFuture

Java 8 came up with tons of new features and enhancements like Lambda expressions, Streams, CompletableFutures etc. In this post I’ll give you a detailed explanation of CompletableFuture and all its methods using simple examples.

## What’s a CompletableFuture?

```java CompletableFuture ``` is used for asynchronous programming in Java. Asynchronous programming is a means of writing non-blocking code by running a task on a separate thread than the main application thread and notifying the main thread about its progress, completion or failure.

This way, your main thread does not block/wait for the completion of the task and it can execute other tasks in parallel.

Having this kind of parallelism greatly improves the performance of your programs.

Java 8 came up with tons of new features and enhancements like Lambda expressions, Streams, CompletableFutures etc. In this post I’ll give you a detailed explanation of CompletableFuture and all its methods using simple examples.

## What’s a CompletableFuture?
CompletableFuture is used for asynchronous programming in Java. Asynchronous programming is a means of writing non-blocking code by running a task on a separate thread than the main application thread and notifying the main thread about its progress, completion or failure.

This way, your main thread does not block/wait for the completion of the task and it can execute other tasks in parallel.

Having this kind of parallelism greatly improves the performance of your programs.




 
## Future vs CompletableFuture
CompletableFuture is an extension to Java’s Future API which was introduced in Java 5.

A Future is used as a reference to the result of an asynchronous computation. It provides an isDone() method to check whether the computation is done or not, and a get() method to retrieve the result of the computation when it is done.

You can learn more about Future from my Callable and Future Tutorial.

Future API was a good step towards asynchronous programming in Java but it lacked some important and useful features -

## Limitations of Future
It cannot be manually completed :
*  Let’s say that you’ve written a function to fetch the latest price of an e-commerce product from a remote API. Since this API call is time-consuming, you’re running it in a separate thread and returning a Future from your function.<br/>
 Now, let’s say that If the remote API service is down, then you want to complete the Future manually by the last cached price of the product.<br/>
 Can you do this with Future? No!

* You cannot perform further action on a Future’s result without blocking:<br/>
Future does not notify you of its completion. It provides a get() method which blocks until the result is available.<br/>
You don’t have the ability to attach a callback function to the Future and have it get called automatically when the Future’s result is available.
* Multiple Futures cannot be chained together :<br/>
Sometimes you need to execute a long-running computation and when the computation is done, you need to send its result to another long-running computation, and so on.<br/>
You can not create such asynchronous workflow with Futures.

* You can not combine multiple Futures together :<br/>
Let’s say that you have 10 different Futures that you want to run in parallel and then run some function after all of them completes. You can’t do this as well with Future.

* No Exception Handling :<br/>
Future API does not have any exception handling construct.

Whoa! So many limitations right? Well, That’s why we have CompletableFuture. You can achieve all of the above with CompletableFuture.

CompletableFuture implements Future and CompletionStage interfaces and provides a huge set of convenience methods for creating, chaining and combining multiple Futures. It also has a very comprehensive exception handling support.

@see <a href="https://www.callicoder.com/java-8-completablefuture-tutorial/#future-vs-completablefuture"> https://www.callicoder.com/java-8-completablefuture-tutorial/#future-vs-completablefuture</a>