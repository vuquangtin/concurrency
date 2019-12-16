/**
 * 
 */
package com.handson.generics;

import java.io.Serializable;

/**
 * @author veera
 *
 */
public class GenericsTest4 {

	public interface DemoInterface<T1, T2> {
		T2 doSomeOperation(T1 t);

		T1 doReverseOperation(T2 t);
	}

	// This is a generic code implementation.
	public class DemoClass1 implements DemoInterface<String, Integer> {

		@Override
		public Integer doSomeOperation(String t) {
			return null;
		}

		@Override
		public String doReverseOperation(Integer t) {
			return null;
		}
	}

	// This is a raw type code implementation.
	public class DemoClass2 implements DemoInterface {

		@Override
		public Object doSomeOperation(Object t) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object doReverseOperation(Object t) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/*
	 * Extending/implementing Generic types using another generic type
	 * parameter(Using generic upper bound types in this example)
	 */
	public class DemoClass3<T extends Cloneable, U extends Serializable> implements DemoInterface<T, U> {

		@Override
		public U doSomeOperation(T t) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public T doReverseOperation(U t) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
