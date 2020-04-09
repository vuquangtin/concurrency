package threadpools;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class CountingTask extends RecursiveTask<Integer> {
	private final TreeNode node;

	public CountingTask(TreeNode node) {
		this.node = node;
	}

	@Override
	protected Integer compute() {
		return node.value + node.children.stream().map(childNode -> new CountingTask(childNode).fork())
				.collect(Collectors.summingInt(ForkJoinTask::join));
	}
}