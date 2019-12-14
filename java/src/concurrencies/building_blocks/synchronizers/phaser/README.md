# Phaser in Java

Many synchronization aid were added as the part of java.util.concurrent package in Java 5 like Semaphore, Exchanger. One more synchronization aid added as part of Java concurrency is Phaser which was added in Java 7.

## Phaser in Java concurrency
Phaser in Java is also a synchronization barrier like CountDownLatch and CyclicBarrier where threads need to wait at a barrier until all the threads have reached the barrier that is when barrier is tripped. Phaser offers more flexibility by synchronizing threads over multiple phases. In each phase threads can be dynamically registered and unregistered.

Some important points about Phaser in Java are as follows-

* Using Phaser you can synchronize over multiple phases reusing the same phaser instance.
* Once all the threads register for a phase arrive at a barrier that phase is considered complete and the next phase starts.
* You can use Phaser to synchronize a single phase too but it is more useful when used to synchronize multiple phases.

## Java Phaser constructors
There are four constructors in the Phaser class.

* Phaser()– Creates a new phaser with no initially registered parties, no parent, and initial phase number 0.
* Phaser(int parties)– Creates a new phaser with the given number of registered unarrived parties, no parent, and initial phase number 0.
* Phaser(Phaser parent, int parties)– Creates a new phaser with the given parent and number of registered unarrived parties.
* Phaser(Phaser parent)– Creates a new phaser with the given parent and no initially registered parties.

## How Phaser works in Java
1. First thing is to create an instance of Phaser.
2. Register parties with phaser by calling the register() method, you can also use the constructor where number of parties is passed as an argument.
3. To signal that the party has arrived at a phase one of the arrive() method has to be called. Once all the registered parties have arrived at a phase that phase is considered complete.
4. Each generation of a phaser has an associated phase number. The phase number starts at zero, and advances when all parties arrive at the phaser.

## Phaser example in Java
Here is an example showing phaser in action. There are two runnable tasks which will be executed as two separate phases. First runnable task (FirstTask) is executed by three threads, for that you can see 3 parties are registered using bulkRegister() method.

For second runnable task (SecondTask) register method is called with in the runnable class.

```java
package synchronizers.phaser;

import java.util.concurrent.Phaser;

public class ThreePhaserDemo {

	public static void main(String[] args) {
		Phaser ph = new Phaser(1);
		// registering 3 parties in bulk
		ph.bulkRegister(3);
		System.out.println("Phase in Main " + ph.getPhase() + " started");
		// starting 3 threads
		for (int i = 0; i < 3; i++) {
			new Thread(new FirstTask("Thread-" + i, ph)).start();
		}
		int curPhase = ph.getPhase();
		// This is to make main thread wait
		ph.arriveAndAwaitAdvance();
		System.out.println("Phase in Main " + curPhase + " completed");

		for (int i = 0; i < 2; i++) {
			new Thread(new SecondTask("Thread-" + i, ph)).start();
		}
		ph.arriveAndAwaitAdvance();
		System.out.println("Phase in Main-2 " + ph.getPhase() + " completed");
		// deregistering the main thread
		ph.arriveAndDeregister();
	}
}

class FirstTask implements Runnable {
	private String threadName;
	private Phaser ph;

	FirstTask(String threadName, Phaser ph) {
		this.threadName = threadName;
		this.ph = ph;
	}

	@Override
	public void run() {
		System.out.println("In First Task.. " + threadName);
		// parties will wait here
		ph.arriveAndAwaitAdvance();

		System.out.println("Deregistering, Phase- " + ph.getPhase() + " Completed");
		ph.arriveAndDeregister();
	}
}

class SecondTask implements Runnable {
	private String threadName;
	private Phaser ph;

	SecondTask(String threadName, Phaser ph) {
		this.threadName = threadName;
		this.ph = ph;
		ph.register();
	}

	@Override
	public void run() {
		System.out.println("In SecondTask.. " + threadName);
		ph.arriveAndAwaitAdvance();
		System.out.println("In SecondTask.. Phase-" + ph.getPhase() + " completed" + threadName);
		ph.arriveAndDeregister();
	}
}
```

Output

```java
Phase in Main 0 started
In First Task.. Thread-0
In First Task.. Thread-1
In First Task.. Thread-2
Deregistering, Phase- 1 Completed
Phase in Main 0 completed
Deregistering, Phase- 1 Completed
Deregistering, Phase- 1 Completed
In SecondTask.. Thread-0
In SecondTask.. Thread-1
Phase in Main-2 2 completed
In SecondTask.. Phase-2 completedThread-0
In SecondTask.. Phase-2 completedThread-1
```

## Methods in Phaser class
Some of the important methods of the Phaser class in Java are listed below-

* arrive()– Arrives at this phaser, without waiting for others to arrive.
* arriveAndAwaitAdvance()– Arrives at this phaser and awaits others.
* arriveAndDeregister()– Arrives at this phaser and deregisters from it without waiting for others to arrive.
* awaitAdvance(int phase)– Awaits the phase of this phaser to advance from the given phase value, returning immediately if the current phase is not equal to the given phase value or this phaser is terminated.
* bulkRegister(int parties)– Adds the given number of new unarrived parties to this phaser.
getArrivedParties()– Returns the number of registered parties that have arrived at the current phase of this phaser.
* getParent()– Returns the parent of this phaser, or null if none.
* getPhase()– Returns the current phase number.
isTerminated()– Returns true if this phaser has been terminated.
* onAdvance(int phase, int registeredParties)– Overridable method to perform an action upon impending phase advance, and to control termination.
* register()– Adds a new unarrived party to this phaser.

That’s all for the topic Phaser in Java. If something is missing or you have something to share about the topic please write a comment.