package immutable.objects;

/**
 * make this class final, no one can extend this class
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

public final class ImmutableExample {

	private String name;

	ImmutableExample(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	// no setter

	public static void main(String[] args) {

		ImmutableExample obj = new ImmutableExample("mkyong");
		System.out.println(obj.getName());

		// there is no way to update the name after the object is created.
		// obj.setName("new mkyong");
		// System.out.println(obj.getName());

	}
}
