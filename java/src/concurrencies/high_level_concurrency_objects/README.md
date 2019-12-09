# High Level Concurrency Objects

So far, this lesson has focused on the low-level APIs that have been part of the Java platform from the very beginning. These APIs are adequate for very basic tasks, but higher-level building blocks are needed for more advanced tasks. This is especially true for massively concurrent applications that fully exploit today's multiprocessor and multi-core systems.

In this section we'll look at some of the high-level concurrency features introduced with version 5.0 of the Java platform. Most of these features are implemented in the new java.util.concurrent packages. There are also new concurrent data structures in the Java Collections Framework.

 * Lock objects support locking idioms that simplify many concurrent applications.
 * Executors define a high-level API for launching and managing threads. Executor implementations provided by java.util.concurrent provide thread pool management suitable for large-scale applications.
 * Concurrent collections make it easier to manage large collections of data, and can greatly reduce the need for synchronization.
 * Atomic variables have features that minimize synchronization and help avoid memory consistency errors.
 * ThreadLocalRandom (in JDK 7) provides efficient generation of pseudorandom numbers from multiple threads.

