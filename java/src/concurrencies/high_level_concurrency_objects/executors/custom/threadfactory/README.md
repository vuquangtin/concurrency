# Understanding Custom ThreadFactory In Java
@see https://wilddiary.com/understanding_custom_threadfactory_in_java/
A thread factory encapsulates the logic of creation and configuration of threads. It creates and returns thread instances on demand. You can see it being used in the JDK’s executor framework. Every thread pool has an associated thread factory that creates the threads for the pool. The thread factory not just creates thread instances but also configures them. A thread factory usually does the following:

>1. Names the threads
>2. Sets daemon status
>3. Assigns thread priority
>4. Assigns the thread to a thread group
>5. Sets handler for uncaught exceptions

## The Default ThreadFactory
The executor framework creates thread pools by accepting a user specified thread factory or else uses a default thread factory, if  not specified. The default thread factory that the executor framework uses is defined as a static inner class in java.util.concurrent.Executors class. It basically creates named user threads with names such as pool-1-thread-1, pool-2-thread-3, … pool-n-thread-m. It uses an incremental pool number and thread number naming scheme such as pool-<pool number>-thread-<thread number>. All threads that it creates have normal priority. The DefaultThreadFactory code is shown below:

```java
/**
     * The default thread factory
     */
    static class DefaultThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;
 
        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null)? s.getThreadGroup() :
                                 Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
        }
 
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
```

## Why do I need a custom ThreadFactory?
It is always a good idea to specify your own custom thread factory to control the configuration of the threads being created. With the default thread factory you cannot control much of it. We discuss the reasons one by one below.

>1. To have custom thread names
>2. To choose between thread types
>3. To choose Thread Priority
>4. To handle uncaught exceptions

### To have custom thread names
The default thread factory does not allow you to specify custom thread names. All the threads that it creates are named as pool-1-thread-1, pool-2-thread-3 and so on. Imagine if you need to debug/profile an environment with such thread names, it would be a nightmare to identify which thread belongs to what pool and which one does what. It would not be a severe problem for small projects with a single thread pool where you anyway know where the threads belong and what the pool is for.  But, for projects with multiple thread pools, it would be a nightmare. Instead, threads with more descriptive names indicative of their job function would help quickly identify and debug/profile the thread you are interested in. Hence, to control the naming scheme and aide debugging, you would need a custom ThreadFactory.

### To choose between thread types
The default thread factory only produces user threads. It cannot produce daemon threads. User threads prevent JVM from exiting while daemon threads do not, meaning, if no user thread is running, JVM will exit irrespective of daemon thread(s) running. For implementing background services, daemon threads are preferred. For eg. Garbage Collector runs as a daemon thread.

### To choose Thread Priority
The default thread factory creates threads with normal priority.  But sometimes you may need to create prioritized threads. Higher priority threads are executed in preference to lower priority threads.

### To handle uncaught exceptions
When an uncaught runtime exception occurs in a thread when executing a task, by default, the thread is terminated and the exception is logged to the console. This default behavior may not be always appropriate. For eg. You may need to log the exception to a log file or update a database to record the failing task. You can achieve this by setting a custom uncaught exception handler on the thread created, in the custom thread factory, to handle the uncaught exceptions as desired.

## Creating Custom Thread Factories
Writing a custom thread factory is fairly easy. All you need to do is, implement the java.util.concurrent.ThreadFactory interface. The ThreadFactory interface has a single method called newThread(Runnable r) which accepts a Runnable type and returns a thread instance. Your factory logic goes into this method implementation where you create and configure the thread instance. To make things simple, you can directly copy the code DefaultThreadFactory class, rename the class and change the thread configuration as needed. This is fine if you ever need, not more than one thread pool. But, what if you need to create thread pools more often than once. You cannot be creating a new thread factory class (even anonymous classes, for that matter) for each pool.

### An Elegant Solution based on the builder pattern
A more elegant solution would be to be able to create customized thread factory instances using a thread factory builder mechanism. Below is a sample implementation.

```java
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
 
public class ThreadFactoryBuilder {
    private String namePrefix = null;
    private boolean daemon = false;
    private int priority = Thread.NORM_PRIORITY;
    private ThreadFactory backingThreadFactory = null;
    private UncaughtExceptionHandler uncaughtExceptionHandler = null;
 
    public ThreadFactoryBuilder setNamePrefix(String namePrefix) {
        if (namePrefix == null) {
            throw new NullPointerException();
        }
        this.namePrefix = namePrefix;
        return this;
    }
 
    public ThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }
 
    public ThreadFactoryBuilder setPriority(int priority) {
        if (priority < Thread.MIN_PRIORITY) {
            throw new IllegalArgumentException(String.format(
                    "Thread priority (%s) must be >= %s", priority,
                    Thread.MIN_PRIORITY));
        }
 
        if (priority > Thread.MAX_PRIORITY) {
            throw new IllegalArgumentException(String.format(
                    "Thread priority (%s) must be <= %s", priority,
                    Thread.MAX_PRIORITY));
        }
 
        this.priority = priority;
        return this;
    }
 
    public ThreadFactoryBuilder setUncaughtExceptionHandler(
            UncaughtExceptionHandler uncaughtExceptionHandler) {
        if (null == uncaughtExceptionHandler) {
            throw new NullPointerException(
                    "UncaughtExceptionHandler cannot be null");
        }
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }
 
    public ThreadFactoryBuilder setThreadFactory(
            ThreadFactory backingThreadFactory) {
        if (null == uncaughtExceptionHandler) {
            throw new NullPointerException(
                    "BackingThreadFactory cannot be null");
        }
        this.backingThreadFactory = backingThreadFactory;
        return this;
    }
 
    public ThreadFactory build() {
        return build(this);
    }
 
    private static ThreadFactory build(ThreadFactoryBuilder builder) {
        final String namePrefix = builder.namePrefix;
        final Boolean daemon = builder.daemon;
        final Integer priority = builder.priority;
        final UncaughtExceptionHandler uncaughtExceptionHandler = builder.uncaughtExceptionHandler;
        final ThreadFactory backingThreadFactory = (builder.backingThreadFactory != null) ? builder.backingThreadFactory
                : Executors.defaultThreadFactory();
 
        final AtomicLong count = new AtomicLong(0);
 
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = backingThreadFactory.newThread(runnable);
                if (namePrefix != null) {
                    thread.setName(namePrefix + "-" + count.getAndIncrement());
                }
                if (daemon != null) {
                    thread.setDaemon(daemon);
                }
                if (priority != null) {
                    thread.setPriority(priority);
                }
                if (uncaughtExceptionHandler != null) {
                    thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
                }
                return thread;
            }
        };
    }
}
```
We employ the builder design pattern to create a ThreadFactoryBuilder class with methods to set thread name, priority, daemon status and an uncaught exception handler. The builder uses a backing thread factory and delegates the thread creation to it.

## Using the ThreadFactoryBuilder class
It is very easy to use the ThreadFactoryBuilder class. Just create a new instance of it and use the setters for the properties that you need to customize and finally call the build() method. The build() method returns a ThreadFactory instance customized as per your specification. You can use this ThreadFactory instance in your thread pools.

>Important: The ThreadFactory instance should not be shared across thread pools. Use one factory instance for one pool. Otherwise, all the threads of the pools that share factory would have similar names and you would not be able identify which thread belongs to which pool.

```java
ThreadFactory customThreadfactory = new ThreadFactoryBuilder()
                .setNamePrefix("ImageDownloadersPool-Thread").setDaemon(false)
                .setPriority(Thread.MAX_PRIORITY)
                .setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        System.err.println(String.format(
                                "Thread %s threw exception - %s", t.getName(),
                                e.getMessage()));
 
                    }
                }).build();
 
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3,
                customThreadfactory);
```
Using a backing thread factory, allows you to set only those parameters on the builder that you want to customize and rest come as default. For eg. You may just need to customize the thread naming scheme over what the default the DefaultThreadFactory provides. In this case all you need to do is as shown below:

```java
ThreadFactory customThreadfactory = new ThreadFactoryBuilder()
                .setNamePrefix("ImageDownloadersPool-Thread").build();
```