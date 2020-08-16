package concurrency.java.bounded.blocking;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */

public class ConcurrentStack<E> {
	private final AtomicReference<Node<E>> top = new AtomicReference<>();

	public void push(E elem) {
		Node<E> newHead = new Node<>(elem);
		Node<E> oldHead;
		do {
			oldHead = top.get();
			newHead.next = oldHead;
		} while (!top.compareAndSet(oldHead, newHead));
	}

	public E pop() {
		Node<E> oldHead;
		Node<E> newHead;
		do {
			oldHead = top.get();
			if (oldHead == null)
				return null;
			newHead = oldHead.next;
		} while (!top.compareAndSet(oldHead, newHead));
		return oldHead.item;
	}

	private static class Node<E> {
		public final E item;
		public Node<E> next;

		public Node(E item) {
			this.item = item;
		}
	}

}