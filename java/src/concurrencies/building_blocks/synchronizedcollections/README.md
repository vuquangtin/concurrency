# An Introduction to Synchronized Java Collections
## Overview

The collections framework is a key component of Java. It provides an extensive number of interfaces and implementations, which allows us to create and manipulate different types of collections in a straightforward manner.

Although using plain unsynchronized collections is simple overall, it can also become a daunting and error-prone process when working in multi-threaded environments (a.k.a. concurrent programming).

Hence, the Java platform provides strong support for this scenario through different synchronization wrappers implemented within the Collections class.

These wrappers make it easy to create synchronized views of the supplied collections by means of several static factory methods.

In this tutorial, we'll take a deep dive into these static synchronization wrappers. Also, we'll highlight the difference between synchronized collections and concurrent collections.

## The synchronizedCollection() Method

The first synchronization wrapper that we'll cover in this round-up is the synchronizedCollection() method. As the name suggests, it returns a thread-safe collection backed up by the specified Collection.

Now, to understand more clearly how to use this method, let's create a basic unit test:

```java	
Collection<Integer> syncCollection = Collections.synchronizedCollection(new ArrayList<>());
    Runnable listOperations = () -> {
        syncCollection.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
    };
     
    Thread thread1 = new Thread(listOperations);
    Thread thread2 = new Thread(listOperations);
    thread1.start();
    thread2.start();
    thread1.join();
    thread2.join();
     
    assertThat(syncCollection.size()).isEqualTo(12);
}
```

As shown above, creating a synchronized view of the supplied collection with this method is very simple.

To demonstrate that the method actually returns a thread-safe collection, we first create a couple of threads.

After that, we then inject a Runnable instance into their constructors, in the form of a lambda expression. Let's keep in mind that Runnable is a functional interface, so we can replace it with a lambda expression.

Lastly, we just check that each thread effectively adds six elements to the synchronized collection, so its final size is twelve.

## The synchronizedList() Method

Likewise, similar to the synchronizedCollection() method, we can use the synchronizedList() wrapper to create a synchronized List.

As we might expect, the method returns a thread-safe view of the specified List:

```java	
List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
```

Unsurprisingly, the use of the synchronizedList() method looks nearly identical to its higher-level counterpart, synchronizedCollection().

Therefore, as we just did in the previous unit test, once that we've created a synchronized List, we can spawn several threads. After doing that, we'll use them to access/manipulate the target List in a thread-safe fashion.

In addition, if we want to iterate over a synchronized collection and prevent unexpected results, we should explicitly provide our own thread-safe implementation of the loop. Hence, we could achieve that using a synchronized block:

```java	
List<String> syncCollection = Collections.synchronizedList(Arrays.asList("a", "b", "c"));
List<String> uppercasedCollection = new ArrayList<>();
     
Runnable listOperations = () -> {
    synchronized (syncCollection) {
        syncCollection.forEach((e) -> {
            uppercasedCollection.add(e.toUpperCase());
        });
    }
};
```
In all cases where we need to iterate over a synchronized collection, we should implement this idiom. This is because the iteration on a synchronized collection is performed through multiple calls into the collection. Therefore they need to be performed as a single atomic operation.

The use of the synchronized block ensures the atomicity of the operation.

## The synchronizedMap() Method

The Collections class implements another neat synchronization wrapper, called synchronizedMap(). We could use it for easily creating a synchronized Map.

The method returns a thread-safe view of the supplied Map implementation:

```java		
Map<Integer, String> syncMap = Collections.synchronizedMap(new HashMap<>());
```

## The synchronizedSortedMap() Method

There's also a counterpart implementation of the synchronizedMap() method. It is called synchronizedSortedMap(), which we can use for creating a synchronized SortedMap instance:

```java	
	
Map<Integer, String> syncSortedMap = Collections.synchronizedSortedMap(new TreeMap<>());
```

## The synchronizedSet() Method

Next, moving on in this review, we have the synchronizedSet() method. As its name implies, it allows us to create synchronized Sets with minimal fuss.

The wrapper returns a thread-safe collection backed by the specified Set:

```java	
Set<Integer> syncSet = Collections.synchronizedSet(new HashSet<>());
```

## The synchronizedSortedSet() Method

Finally, the last synchronization wrapper that we'll showcase here is synchronizedSortedSet().

Similar to other wrapper implementations that we've reviewed so far, the method returns a thread-safe version of the given SortedSet:

```java		
SortedSet<Integer> syncSortedSet = Collections.synchronizedSortedSet(new TreeSet<>());
```

## Synchronized vs Concurrent Collections

Up to this point, we took a closer look at the collections framework's synchronization wrappers.

Now, let's focus on the differences between synchronized collections and concurrent collections, such as ConcurrentHashMap and BlockingQueue implementations.

### Synchronized Collections

Synchronized collections achieve thread-safety through intrinsic locking, and the entire collections are locked. Intrinsic locking is implemented via synchronized blocks within the wrapped collection's methods.

As we might expect, synchronized collections assure data consistency/integrity in multi-threaded environments. However, they might come with a penalty in performance, as only one single thread can access the collection at a time (a.k.a. synchronized access).

For a detailed guide on how to use synchronized methods and blocks, please check our article on the topic.

### Concurrent Collections

Concurrent collections (e.g. ConcurrentHashMap), achieve thread-safety by dividing their data into segments. In a ConcurrentHashMap, for example, different threads can acquire locks on each segment, so multiple threads can access the Map at the same time (a.k.a. concurrent access).

Concurrent collections are much more performant than synchronized collections, due to the inherent advantages of concurrent thread access.

So, the choice of what type of thread-safe collection to use depends on the requirements of each use case, and it should be evaluated accordingly.

## Conclusion

In this article, we took an in-depth look at the set of synchronization wrappers implemented within the Collections class.
Additionally, we highlighted the differences between synchronized and concurrent collections, and also looked at the approaches they implement for achieving thread-safety.
