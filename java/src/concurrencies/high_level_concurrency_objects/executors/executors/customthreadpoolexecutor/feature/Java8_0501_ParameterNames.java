package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Java8_0501_ParameterNames {
	
	public static void main(String[] args) throws Exception {
		Method method = Java8_0501_ParameterNames.class.getMethod("main", String[].class);
		for (final Parameter parameter : method.getParameters()) {
			out.println("Parameter: " + parameter.getName());
		}
	}
	
}
