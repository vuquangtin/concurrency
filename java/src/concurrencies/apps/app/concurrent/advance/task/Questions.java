package app.concurrent.advance.task;

/*-
 * Please read following questions and create a separate answer class with name: Answer<no>.java
 *
 * You could take code sections from Part files along with any help from Google. :)
 */

class BasicQuestions {

	/*-
	 1. - Write a short note on differences among: Synchronization vs Atomic variable vs Volatile variable.
	 - Write a class to demonstrate that internal resources(variables) have same shared addresses if participating threads use
	 shared runnable instance. Use 3 threads where two of them share same runnable instance and the other one use a new runnable instance.
	 hint: System.out.println(object): will print object's value with its integer address via an "@" sign.
	 Same address means it's the very same object used by two different threads and that's why it is called a "shared object".
	 2. Based on Part3 and Part4, write an example class which proves, volatile variable doesn't prove harmful race conditions.
	 Hint: You could just copy the required code sections from Part3.
	 3. Write an example class demonstrating the use of ThreadLocal class (from Part4). Let Google help you. :)
	 4. Based on Part5 class, implement following multiple task dependencies: task1->task3->task2
	 5. Checkout the example2/producer-consumer problem in - http://www.programcreek.com/2009/02/notify-and-wait-example/
	 -  Will their example2 face missing-signal/spurious wake up problems?
	 -  Now re-Write the producer-consumer example using the waitNotifierThree class from Part5.
	 * */
}

/**
 * Questions from intermediate parts.
 */

class IntermediateQuestions {
	/*-
	 1. How to stop a thread? Example a case. Let google help you.
	 2. Solve our consume-producer problem of Question5/Basic_part via ReentrantLock class.
	 3. Example a deadlock prevention class via ReentrantLock's tryLock() methods.
	 * */
}

/**
 * Please read following questions and create a separate answer class with name:
 * Answer<no>.java
 *
 * You could take code sections from Part files along with any help from Google.
 * :)
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public class Questions {

	/*-
		1. Answer/port our consumer-producer answer from intermediate/task/Answer2 via Executor service.
		2. Convert Part12 class into two section: in one section urls are fetched/invoked normally, concurrently in another and then
		calculate their time differences.
		3. Collect all the data from the urls in Part12 and write those data in files separately. File operations must be done in separate threads.
		4. Grand finale: Final task
		- Make a copy of HttpInvoker class and transform it into a thread safe http class
		(do all required R&Ds first + checkout other thread safe HttpInvoker implementations)
		- Use a single instance of our thread safe invoker class to solve question #3.
		- For task's completion waiting, use Phaser class.
	 * */
}