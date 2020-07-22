package immutable.objects;

/**
 * ImmutableDogBuilder dogBuilder = new
 * ImmutableDogBuilder().setName("Rover").setWeight(25);
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ImmutableDogBuilder {
	private String name;
	private int weight;

	public ImmutableDogBuilder() {
	}

	public ImmutableDog build() {
		return new ImmutableDog(name, weight);
	}

	public ImmutableDogBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public ImmutableDogBuilder setWeight(int weight) {
		this.weight = weight;
		return this;
	}

	public String getName() {
		return this.name;
	}

	public int getWeight() {
		return this.weight;
	}
}
