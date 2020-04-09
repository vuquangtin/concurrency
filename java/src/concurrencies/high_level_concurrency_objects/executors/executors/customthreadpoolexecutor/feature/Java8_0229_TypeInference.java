package executors.customthreadpoolexecutor.feature;

public class Java8_0229_TypeInference {
	public static void main(String[] args) {
		final Java8_0229_Value<String> value = new Java8_0229_Value<>();
		value.getOrDefault("22", Java8_0229_Value.defaultValue());
	}
}
