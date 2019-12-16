/**
 * 
 */
package com.handson.generics;

import java.util.Collections;
import java.util.List;

/**
 * @author veera
 *
 */
public class GenericsTest7 {

	public static void main(String[] args) {
		/*
		 * Compiler infers target type from method call and identifies it as String
		 * type.So, return type of Collections.emptyList is List<String> Object.
		 */
		addGrandChildren(Collections.emptyList());
	}

	public static void addGrandChildren(List<? super String> stringSuperObjects) {
		System.out.println(stringSuperObjects);
	}

}
