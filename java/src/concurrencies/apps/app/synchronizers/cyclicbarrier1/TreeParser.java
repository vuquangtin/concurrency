package app.synchronizers.cyclicbarrier1;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class TreeParser implements Runnable {

	DataNode myNode;
	boolean go;
	private CyclicBarrier myBarrier;

	public TreeParser(DataNode node, CyclicBarrier barrier) {
		myNode = node;
		myBarrier = barrier;
		go = true;
	}

	private void cycleStatuses(DataNode node) {
		ArrayList<DataNode> queue = new ArrayList();
		DataNode currentNode;
		queue.add(node);
		while (queue.size() > 0) {
			// currentNode = queue.get(0); // for BREADTH
			currentNode = queue.get(queue.size() - 1); // for BREADTH
			currentNode.cycleStatus();
			try {
				myBarrier.await();
			} catch (InterruptedException ex) {
				System.out.println("Doublecrap!");
			} catch (BrokenBarrierException e) {
			}
			queue.remove(currentNode);
			if (currentNode.isParent()) {
				for (int i = 0; i < currentNode.childNodes.size(); i++) {
					queue.add(currentNode.childNodes.get(i));
				}
			}
		}
	}

	public void run() {
		while (go) {
			cycleStatuses(myNode);

		}
	}
}
