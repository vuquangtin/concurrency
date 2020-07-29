package concurrency.java.memory.model.jmm;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class HeapStack {

	// main() method thread creates space in stack memory
	public static void main(String[] args) {

		// primitive datatype created inside main() method space in stack memory
		int i = 1;

		// Object created in heap memory and its refference obj in stack memory
		Object obj = new Object();

		// Heap_Stack Object created in heap memory and its refference objnew in
		// stack memory
		HeapStack objnew = new HeapStack();

		// New space for foo() method created in the top of the stack memory
		objnew.foo(obj);

	}

	private void foo(Object p) {

		// String for p.toString() is created in String Pool and refference str
		// created in stack memory
		String str = p.toString();

		System.out.println(str);
	}
}