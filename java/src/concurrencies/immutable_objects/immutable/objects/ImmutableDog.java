package immutable.objects;

/**
 * The builder pattern creates a temporary object with the same fields as the
 * desired object. It has getters and setters for all the fields. It also has a
 * build() method that creates the desired object
 * 
 * Imagine a small immutable object:
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ImmutableDog {
	private final String name;
	private final int weight;

	public ImmutableDog(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	public String getName() {
		return this.name;
	}

	public int getWeight() {
		return this.weight;
	}
}
