# ExecutorService vs ExecutorCompletionService in Java
Suppose we have list of four tasks: Task A, Task B, Task C and Task D which perform some complex computation and result into an integer value. These tasks may take random time depending upon various parameters. We can submit these tasks to executor as:

```java
ExecutorService executorService = Executors.newFixedThreadPool(4);
List<Future> futures = new ArrayList<Future<Integer>>();
futures.add(executorService.submit(A));
futures.add(executorService.submit(B));
futures.add(executorService.submit(C));
futures.add(executorService.submit(D));
```

Then we can iterate over the list to get the computed result of each future:

```java
for (Future future:futures) {
    Integer result = future.get();
    // rest of the code here.
}
```
Now the similar functionality can also be achieved using ExecutorCompletionService as:

```java
ExecutorService executorService = Executors.newFixedThreadPool(4);
CompletionService executorCompletionService= new ExecutorCompletionService<>(executorService );
```

Then again we can submit the tasks and get the result like:

```java
List<Future> futures = new ArrayList<Future<Integer>>();
futures.add(executorCompletionService.submit(A));
futures.add(executorCompletionService.submit(B));
futures.add(executorCompletionService.submit(C));
futures.add(executorCompletionService.submit(D));

for (int i=0; i<futures.size(); i++) {
    Integer result = executorCompletionService.take().get();
    // Some processing here
}
```

## Now what is the difference between the two? 

Suppose task B finished first followed by task C. But task A was still going on. In that case when using ExecutorService the for loop would be waiting for the result of task A to be available. So in case of ExecutorService tasks will be processed in the same order in which they were submitted.

But in later case the tasks will be processed in order the result becomes available, the order tasks are completed. One interesting example is where we want to download some file which can be downloaded from various mirrors. In that case we can quickly get the response from the server which is located closest to us. In that case we can get the first available result and discard the others. Consider the following example from Java Doc:

```java
void solve(Executor e, Collection<Callable<Result>> solvers) 
      throws InterruptedException {
        CompletionService<Result> ecs = new ExecutorCompletionService<Result>(e);
        int n = solvers.size();
        List<Future<Result>> futures = new ArrayList<Future<Result>>(n);
        Result result = null;
        try {
            for (Callable<Result> s : solvers)
                futures.add(ecs.submit(s));
            for (int i = 0; i < n; ++i) {
                try {
                    Result r = ecs.take().get();
                    if (r != null) {
                        result = r;
                        break;
                    }
                } catch(ExecutionException ignore) {}
            }
        }
        finally {
            for (Future<Result> f : futures)
                f.cancel(true);
        }

        if (result != null)
            use(result);
    }
```

In the above example the moment we get the result we break out of the loop and cancel out all the other futures. One important thing to note is that the implementation ofExecutorCompletionService contains a queue of results. We need to remember the number of tasks we have added to the service and then should use take or poll to drain the queue otherwise a memory leak will occur. Some people use the Future returned by submit to process results and this is NOT correct usage. There is one interesting solution provided by Dr. Heinz M, Kabutz here.


## Peeking into ExecutorCompletionService 
When we look inside the code for this we observe that it makes use of Executor,AbstractExecutorService (default implementations of ExecutorService execution methods) and BlockingQueue (actual instance is of class LinkedBlockingQueue<Future<V>>).

```java
public class ExecutorCompletionService<V> implements CompletionService<V> {
    private final Executor executor;
    private final AbstractExecutorService aes;
    private final BlockingQueue<Future<V>> completionQueue;
    // Remaining code..
}
```
Another important thing to observe is class QueueingFuture which extends FutureTaskand in the method done() the result is pushed to queue.

```java
private class QueueingFuture extends FutureTask<Void> {
        QueueingFuture(RunnableFuture<V> task) {
            super(task, null);
            this.task = task;
        }
        protected void done() { completionQueue.add(task); }
        private final Future<V> task;
    }
```

For the curios ones, class FutureTask is base implementation of Future interface with methods to start and cancel a computation, query to see if the computation is complete, and retrieve the result of the computation. And constructor takes RunnableFuture as parameter which is again an interface which extends Future interface and adds only one method run as:

```java
public interface RunnableFuture<V> extends Runnable, Future<V> {
    /**
     * Sets this Future to the result of its computation
     * unless it has been cancelled.
     */
    void run();
}
```