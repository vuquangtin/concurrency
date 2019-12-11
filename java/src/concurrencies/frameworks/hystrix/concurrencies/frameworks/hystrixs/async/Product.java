package concurrencies.frameworks.hystrixs.async;

public class Product {
	private String name;
	private String decs;

	public Product(String name, String decs) {
		setName(name);
		setDecs(decs);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDecs() {
		return decs;
	}

	public void setDecs(String decs) {
		this.decs = decs;
	}

	@Override
	public String toString() {

		return "name:" + name + "\tdesc:" + decs;
	}
}
