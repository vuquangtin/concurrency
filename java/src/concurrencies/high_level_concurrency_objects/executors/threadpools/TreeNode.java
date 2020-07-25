package threadpools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class TreeNode {
	int value;

	Set<TreeNode> children;

	TreeNode(int value, TreeNode... children) {
		this.value = value;
		this.children = new HashSet<>(Arrays.asList(children));
	}
}