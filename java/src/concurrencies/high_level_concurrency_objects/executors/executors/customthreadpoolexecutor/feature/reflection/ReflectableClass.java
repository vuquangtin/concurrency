package executors.customthreadpoolexecutor.feature.reflection;

@Reflectable
public class ReflectableClass {
	@Reflectable
	public int methodReflectable() {
		return 1;
	}

	public int methodNotReflectable() {
		return 1;
	}
}
