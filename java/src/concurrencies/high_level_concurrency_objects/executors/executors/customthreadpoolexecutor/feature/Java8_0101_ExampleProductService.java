package executors.customthreadpoolexecutor.feature;

public interface Java8_0101_ExampleProductService {

	String getProductName();

	Integer getProductPrice();

	default String getInfo() {
		return getProductName() + ", " + getProductPrice();
	}

	static String getVehicleInfo(ExampleProductDto c) {
		return c.getProductName() + ", " + c.getProductPrice();
	}
}
