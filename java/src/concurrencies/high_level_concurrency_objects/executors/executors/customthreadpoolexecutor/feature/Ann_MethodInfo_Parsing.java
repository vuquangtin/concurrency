package executors.customthreadpoolexecutor.feature;

import static java.lang.System.out;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Ann_MethodInfo_Parsing {

	public static void main(String[] args) {

		try {
			for (Method method : Ann_MethodInfo_Parsing.class.getClassLoader()
					.loadClass(("example.java.features.Ann_MethodInfo_App")).getMethods()) {

				// Check presence of Ann_MethodInfo annotation
				if (method.isAnnotationPresent(executors.customthreadpoolexecutor.feature.Ann_MethodInfo.class)) {
					try {

						// iterates all the annotations available in the method
						for (Annotation anno : method.getDeclaredAnnotations()) {
							out.println("Method: " + method + ", Annotation: " + anno);
						}

						// access annotation value
						Ann_MethodInfo annoMethod = method.getAnnotation(Ann_MethodInfo.class);
						if (annoMethod.revision() == 1) {
							out.println("Ann_MethodInfo revision: " + method);
						}

					} catch (Throwable ex) {
						ex.printStackTrace();
					}
				}
				out.println();
			}
		} catch (SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
