package executors.customthreadpoolexecutor.feature.reflection;

//Interfaces and reflection
public class ReflectionInterfaces {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		// can be accessed like a class
		System.out.println("interface name: " + InterfaceExample.class.getName());
		// cannot be instantiated: java.lang.InstantiationException
		InterfaceExample.class.newInstance();
	}
}
