package executors.extended;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ReflectedRunnable implements Runnable {

	private final Object object;
	private final Method method;

	public ReflectedRunnable(Object object, String methodName) {

		super();
		this.object = object;

		method = ReflectionUtils.getMethod(object.getClass(), methodName, 0);

		if (method == null) {

			throw new RuntimeException("Method " + methodName
					+ "() not found in class " + object.getClass());
		}

		if (!method.isAccessible()) {

			ReflectionUtils.fixMethodAccess(method);
		}
	}

	public void run() {

		try {
			method.invoke(object);

		} catch (IllegalArgumentException e) {

			throw new RuntimeException(e);

		} catch (IllegalAccessException e) {

			throw new RuntimeException(e);

		} catch (InvocationTargetException e) {

			throw new RuntimeException(e);
		}
	}
}	