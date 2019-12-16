/**
 * 
 */
package com.handson.generics;

import java.io.Serializable;

/**
 * @author veera
 *
 */
public class GenericsTest1GenericMethodWith2Types {

	public static void main(String args[]) {

		DummyClass dummyClassObject = new DummyClass();

		Serializable serializable = dummyMethod(dummyClassObject);
		Cloneable cloneable = dummyMethod(dummyClassObject);
	}

	private static <T extends Serializable & Cloneable> T dummyMethod(T genricTypeObject) {
		return genricTypeObject;
	}

	private static class DummyClass implements Serializable, Cloneable {

		private static final long serialVersionUID = 1L;

	}

}
