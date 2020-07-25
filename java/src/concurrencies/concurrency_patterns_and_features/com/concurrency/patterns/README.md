# Concurrency Patterns

@see <a href="https://www.dre.vanderbilt.edu/~schmidt/POSA/POSA2/conc-patterns.html">https://www.dre.vanderbilt.edu/~schmidt/POSA/POSA2/conc-patterns.html</a>

This chapter presents five patterns that address various types of concurrency architecture and design issues for components, subsystems, and applications: Active Object, Monitor Object, Half-Sync/Half-Async, Leader/Followers, and Thread-Specific Storage.

The choice of concurrency architecture has a significant impact on the design and performance of multi-threaded networking middleware and applications. No single concurrency architecture is suitable for all workload conditions and hardware and software platforms. The patterns in this chapter therefore collectively provide solutions to a variety of concurrency problems.

The first two patterns in this chapter specify designs for sharing resources among multiple threads or processes:

* The Active Object design pattern decouples method execution from method invocation. Its purpose is to enhance concurrency and simplify synchronized access to objects that reside in their own threads of control
* The Monitor Object design pattern synchronizes concurrent method execution to ensure that only one method at a time runs within an object. It also allows an object's methods to schedule their execution sequences cooperatively.

Both patterns can synchronize and schedule methods invoked concurrently on objects. The main difference is that an active object executes its methods in a different thread than its clients, whereas a monitor object executes its methods by borrowing the thread of its clients. As a result active objects can perform more sophisticated--albeit expensive--scheduling to determine the order in which their methods execute.

The next two patterns in this chapter define higher-level concurrency architectures:

* The Half-Sync/Half-Async architectural pattern decouples asynchronous and synchronous processing in concurrent systems, to simplify programming without reducing performance undudly. This pattern introduces two intercommunicating layers, one for asynchronous and one for synchronous service processing. A queuing layer mediates communication between services in the asynchronous and synchronous layers.
* The Leader/Followers architectural pattern provides an efficient concurrency model where multiple threads take turns to share a set of event sources to detect, demultiplex, dispatch, and process service requests that occur on the event sources. The Leader/Followers pattern can be used in lieu of the Half-Sync/Half-Async and Active Object patterns to improve performance when there are no synchronization or ordering constraints on the processing of requests by pooled threads.

Implementors of the Half-Sync/Half-Async and Leader/Followers patterns can use the Active Object and Monitor Object patterns to coordinate access to shared objects efficiently.

The final pattern in this chapter offers a different strategy for addressing certain inherent complexities of concurrency:

* The Thread-Specific Storage design pattern allows multiple threads to use one `logically global' access point to retrieve an object that is local to a thread, without incurring locking overhead on each access to the object. To some extent this pattern can be viewed as the `antithesis' of the other patterns in this section, because it addresses several inherent complexities of concurrency by preventing the sharing of resources among threads.

Implementations of all patterns in this chapter can use the Synchronization patterns presented in Chapter 4 to protect critical regions from concurrent access.

Other patterns in the literature that address concurrency-related issues include Master-Slave [POSA1], Producer-Consumer [Grand98], Scheduler [Lea99a], and Two-phase Termination [Grand98].