# Java Synchronization Tutorial : What, How and Why?

Multithreading and synchronization are a very important topic for any Java programmer. Good knowledge of multithreading, synchronization, and thread-safety can put you in front of other developers, at the same time, it's not easy to master this concept. In fact writing correct concurrent code is one of the hardest things, even in Java, which has several inbuilt synchronization utilities. In this Java synchronization tutorial we will learn what is meaning of Synchronization in Java, Why do we need Synchronization in Java, What is java synchronized keyword, examples of using Java synchronized method and blocks, What can happen in multithreading code in absence of synchronized constructs, tips to avoid mistakes, while locking critical section in Java and some of important points about synchronization in Java.


Since Java provides different constructs to provide synchronization and locking e.g. volatile keyword, atomic variable, explicitly locking using java.util.concurrent.lock.Lock interface and there popular implementations e.g. ReentrantLock and ReentrantReadWriteLock, It becomes even more important to understand difference between synchronized and other constructs. 

Remember, a clear understanding of synchronization is must to write correct concurrent code in Java, which is free of multithreading issues like deadlock, race conditions, and thread-safety. I am sure, things learned in this Java synchronization tutorial will help. Once you went through this article, You can further read Java Concurrency in Practice  to develop your concept.  That's the one of that book which every Java developer must read.

## What is Synchronization in Java
Synchronization in Java is an important concept since Java is a multi-threaded language where multiple threads run in parallel to complete program execution. In multi-threaded environment synchronization of Java object or synchronization of Java class becomes extremely important. Synchronization in Java is possible by using Java keywords "synchronized" and "volatile”. 

Concurrent access of shared objects in Java introduces to kind of errors: thread interference and memory consistency errors and to avoid these errors you need to properly synchronize your Java object to allow mutual exclusive access of critical section to two threads. 

By the way, This Java Synchronization tutorial is in continuation of my article How HashMap works in Java  and difference between HashMap and Hashtable in Java  if you haven’t read already you may find some useful information based on my experience in Java Collections.



Why do we need Synchronization in Java?
If your code is executing in a multi-threaded environment, you need synchronization for objects, which are shared among multiple threads, to avoid any corruption of state or any kind of unexpected behavior. Synchronization in Java will only be needed if shared object is mutable. if your shared object is either read-only or immutable object, then you don't need synchronization, despite running multiple threads. Same is true with what threads are doing with an object if all the threads are only reading value then you don't require synchronization in Java. JVM guarantees that Java synchronized code will only be executed by one thread at a time. 

In Summary, Java synchronized Keyword provides following functionality essential for concurrent programming:


1.  The synchronized keyword in Java provides locking, which ensures mutually exclusive access to the shared resource and prevents data race.
2. synchronized keyword also prevent reordering of code statement by the compiler which can cause a subtle concurrent issue if we don't use synchronized or volatile keyword.
3. synchronized keyword involve locking and unlocking. before entering into synchronized method or block thread needs to acquire the lock, at this point it reads data from main memory than cache and when it release the lock, it flushes write operation into main memory which eliminates memory inconsistency errors.

## Synchronized keyword in Java

Prior to Java 1.5 synchronized keyword was the only way to provide synchronization of shared object in Java. Any code written by using  synchronized block or enclosed inside synchronized method will be mutually exclusive, and can only be executed by one thread at a time. You can have both static synchronized method and nonstatic synchronized method and synchronized blocks in Java but we can not have synchronized variable in java. Using synchronized keyword with a variable is illegal and will result in compilation error. 

Instead of synchronized variable in Java, you can have java volatile variable, which will instruct JVM threads to read the value of the volatile variable from main memory and don’t cache it locally. Block synchronization in Java is preferred over method synchronization in Java because by using block synchronization, you only need to lock the critical section of code instead of the whole method. Since synchronization in Java comes with the cost of performance, we need to synchronize only part of the code which absolutely needs to be synchronized.

## Example of Synchronized Method in Java
Using synchronized keyword along with method is easy just apply synchronized keyword in front of the method. What we need to take care is that static synchronized method locked on class object lock and nonstatic synchronized method locks on current object (this). So it’s possible that both static and nonstatic java synchronized method running in parallel.  This is the common mistake a naive developer do while writing Java synchronized code.

```java
public class Counter{ 
	private static int count = 0; 
	public static synchronized int getCount(){ 
		return count; 
	} 
	public synchoronized setCount(int count){ 
		this.count = count; 
	} 
}

```

In this example of Java, the synchronization code is not properly synchronized because both getCount() and setCount() are not getting locked on the same object and can run in parallel which may result in the incorrect count. Here getCount() will lock in Counter.class object while setCount() will lock on current object (this). To make this code properly synchronized in Java you need to either make both method static or nonstatic or use java synchronized block instead of java synchronized method. By the way, this is one of the common mistake Java developers make while synchronizing their code.


## Example of Synchronized Block in Java
Using synchronized block in java is also similar to using synchronized keyword in methods. Only important thing to note here is that if object used to lock synchronized block of code, Singleton.class in below example is null then Java synchronized block will throw a NullPointerException.

```java
public class Singleton{ 
	private static volatile Singleton _instance; 
	public static Singleton getInstance(){
		 if(_instance == null){ 
		 	synchronized(Singleton.class){ 
		 		if(_instance == null) 
		 			_instance = new Singleton(); 
		 	} 
		 } return _instance; 
	}
```

This is a classic example of double checked locking in Singleton. In this example of Java synchronized code, we have made the only critical section (part of the code which is creating an instance of singleton) synchronized and saved some performance. 

If you make the whole method synchronized than every call of this method will be blocked, while you only need blocking to create singleton instance on the first call. By the way, this is not the only way to write threadsafe singleton in Java.  You can use Enum, or lazy loading to avoid thread-safety issue during instantiation. 

Even above code will not behave as expected because prior to Java 1.5, double checked locking was broken and even with the volatile variable you can view half initialized object. The introduction of Java memory model and happens before guarantee in Java 5 solves this issue. To read more about Singleton in Java see that.
## Important points of synchronized keyword in Java

1. Synchronized keyword in Java is used to provide mutually exclusive access to a shared resource with multiple threads in Java. Synchronization in Java guarantees that no two threads can execute a synchronized method which requires the same lock simultaneously or concurrently.
2. You can use java synchronized keyword only on synchronized method or synchronized block.
3. Whenever a thread enters into java synchronized method or blocks it acquires a lock and whenever it leaves java synchronized method or block it releases the lock. The lock is released even if thread leaves synchronized method after completion or due to any Error or Exception.
4. Java Thread acquires an object level lock when it enters into an instance synchronized java method and acquires a class level lock when it enters into static synchronized java method.
5. Java synchronized keyword is re-entrant in nature it means if a java synchronized method calls another synchronized method which requires the same lock then the current thread which is holding lock can enter into that method without acquiring the lock.
6. Java Synchronization will throw NullPointerException if object used in java synchronized block is null e.g. synchronized (myInstance) will throw java.lang.NullPointerException if myInstance is null.
7. One Major disadvantage of Java synchronized keyword is that it doesn't allow concurrent read, which can potentially limit scalability. By using the concept of lock stripping and using different locks for reading and writing, you can overcome this limitation of synchronized in Java. You will be glad to know that java.util.concurrent.locks.ReentrantReadWriteLock provides ready-made implementation of ReadWriteLock in Java.
8. One more limitation of java synchronized keyword is that it can only be used to control access to a shared object within the same JVM. If you have more than one JVM and need to synchronize access to a shared file system or database, the Java synchronized keyword is not at all sufficient. You need to implement a kind of global lock for that.
9. Java synchronized keyword incurs a performance cost. A synchronized method in Java is very slow and can degrade performance. So use synchronization in java when it absolutely requires and consider using java synchronized block for synchronizing critical section only.
10. Java synchronized block is better than java synchronized method in Java because by using synchronized block you can only lock critical section of code and avoid locking the whole method which can possibly degrade performance. A good example of java synchronization around this concept is getting Instance() method Singleton class. See here.
11. It's possible that both static synchronized and non-static synchronized method can run simultaneously or concurrently because they lock on the different object.
12. From java 5 after a change in Java memory model reads and writes are atomic for all variables declared using the volatile keyword (including long and double variables) and simple atomic variable access is more efficient instead of accessing these variables via synchronized java code. But it requires more care and attention from the programmer to avoid memory consistency errors.
13. Java synchronized code could result in deadlock or starvation while accessing by multiple threads if synchronization is not implemented correctly. To know how to avoid deadlock in java see here.
14. According to the Java language specification you can not use Java synchronized keyword with constructor it’s illegal and result in compilation error. So you can not synchronize constructor in Java which seems logical because other threads cannot see the object being created until the thread creating it has finished it.
15. You cannot apply java synchronized keyword with variables and can not use java volatile keyword with the method.
16. Java.util.concurrent.locks extends capability provided by java synchronized keyword for writing more sophisticated programs since they offer more capabilities e.g. Reentrancy and interruptible locks.
17. Java synchronized keyword also synchronizes memory. In fact, java synchronized synchronizes the whole of thread memory with main memory.
18. Important method related to synchronization in Java are wait(), notify() and notifyAll() which is defined in Object class. Do you know, why they are defined in java.lang.object class instead of java.lang.Thread? You can find some reasons, which make sense.
19. Do not synchronize on the non-final field on synchronized block in Java. because the reference of the non-final field may change anytime and then different thread might synchronizing on different objects i.e. no synchronization at all. an example of synchronizing on the non-final field:
```java
private String lock = new String("lock");
synchronized(lock){
    System.out.println("locking on :"  + lock);
}
```
any if you write synchronized code like above in java you may get a warning "Synchronization on the non-final field"  in IDE like Netbeans and InteliJ
20. It's not recommended to use String object as a lock in java synchronized block because a string is an immutable object and literal string and interned string gets stored in String pool. so by any chance if any other part of the code or any third party library used same String as there lock then they both will be locked on the same object despite being completely unrelated which could result in unexpected behavior and bad performance. instead of String object its advised to use new Object() for Synchronization in Java on synchronized block.
```java
private static final String LOCK = "lock";   //not recommended
private static final Object OBJ_LOCK = new Object(); //better
public void process() {
   synchronized(LOCK) {
      ........
   }
}
```
21. From Java library, Calendar and SimpleDateFormat classes are not thread-safe and requires external synchronization in Java to be used in the multi-threaded environment. and last,

Probably most important point about Synchronization in Java is that in the absence of synchronized keyword or another construct e.g. volatile variable or atomic variable, compiler, JVM, and hardware are free to make optimization, assumption, reordering or caching of code and data, which can cause subtle concurrency bugs in code. By introducing synchronization by using volatile, atomic variable or synchronized keyword, we instruct compiler and JVM to not to do that.

