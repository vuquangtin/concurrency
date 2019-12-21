package basic.oop;

import java.io.*;
import java.lang.reflect.*;
import java.util.jar.*;

final class MyClassWrapper {

	private final Object delegate;

	private MyClassWrapper(Object delegate) {
		this.delegate = delegate;
	}

	void doSomethingElse(boolean yes) {
		try {
			Object obj= delegate.getClass().getMethod("getMaxSelectableDate").invoke(delegate);
			System.out.println(obj.getClass()+":"+obj.toString());
		} catch (InvocationTargetException ex) {
			Throwable cause = ex.getCause();

			if (cause instanceof RuntimeException)
				throw (RuntimeException) cause;
			else if (cause instanceof Error)
				throw (Error) cause;
			else
				throw new AssertionError(ex);

		} catch (Exception ex) {
			throw new AssertionError(ex);
		}
	}

	static final class Factory {
		private final Class<?> clazz;

		Factory(final JarFile jar) throws ClassNotFoundException {
			clazz = Class.forName("com.toedter.calendar.DateUtil", true, new ClassLoader(null) {

				@Override
				protected Class<?> findClass(String name) throws ClassNotFoundException {
					String entryName = name.replaceAll("[.]", "/") + ".class";
					JarEntry entry = jar.getJarEntry(entryName);
					if (entry == null)
						throw new ClassNotFoundException("Jar entry not found");

					byte[] data = new byte[(int) entry.getSize()];
					InputStream in = null;

					try {
						in = jar.getInputStream(entry);
						in.read(data);
						return defineClass(name, data, 0, data.length);
					} catch (IOException ex) {
						throw new ClassNotFoundException("Exception while reading Jar entry", ex);
					} finally {
						if (in != null)
							try {
								in.close();
							} catch (IOException ex) {
								ex.printStackTrace();
							}
					}
				}
			});
		}

		MyClassWrapper build(String s) {
			try {
				Object instance = clazz.getConstructor().newInstance();
				return new MyClassWrapper(instance);
			} catch (InvocationTargetException ex) {
				Throwable cause = ex.getCause();

				if (cause instanceof RuntimeException)
					throw (RuntimeException) cause;
				else if (cause instanceof Error)
					throw (Error) cause;
				else
					throw new AssertionError(ex);

			} catch (Exception ex) {
				throw new AssertionError(ex);
			}
		}
	}
}