/**
 * 
 */
package com.handson.miscellaneous;

/**
 * @author sveera
 *
 */
public class Example05MethodOverridingUsingCovariant {

	/*
	 * Example on method overriding using co-variant type.Returing sub-type during
	 * method overriding is co-varient.
	 */
	public static void main(String[] args) {
		ParentClass parentClass = new SubClass();
		System.out.println(parentClass.getCount());

	}

	private static class ParentClass {

		public Number getCount() {
			return 20;
		}

	}

	private static class SubClass extends ParentClass {

		// Co-varient in return type. Returns sub-type in method overriding.
		@Override
		public Integer getCount() {
			return 20;
		}

	}

}
