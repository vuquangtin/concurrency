/**
 * 
 */
package com.handson.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author veera
 *
 */
public class GenericsTest5 {

	public static void main(String args[]) {

		/*
		 * Use lower bound i.e super to behave like consumer. In the below example add
		 * method behaves like consumer method/operation. However, we cannot call get
		 * method to get Number object.
		 */
		List<? super Number> numbers = new ArrayList<>();
		numbers.add(Integer.valueOf(5));
		// Number value = numbers.get(0);

		/*
		 * Use upper bound i.e extends to behave like producer. In the below example get
		 * method behaves like producer method/operation. However, we cannot call get
		 * method to get Number object.
		 */
		List<? extends Number> numbers1 = new ArrayList<>();
		// numbers1.add(1);
		Number value = numbers1.get(0);

		/*
		 * Below example is related to bounded type using extends.Above examples are
		 * related to upper bound and lower bound examples using wildcard(?).Be carefull
		 * observe the differences between this and above 2 examples.
		 */
		ExtendsTest<Number> extendsTest = new ExtendsTest<Number>();
		extendsTest.getValue();
		extendsTest.setValue(Integer.valueOf(0));

	}

	static class ExtendsTest<T extends Number> {

		private T value;

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

	}
}
