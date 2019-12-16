/**
 * 
 */
package com.handson.generics;

/**
 * @author veera
 *
 */
public class GenericsTest3NameClashWithObjectType {

	/*
	 * Below code will fail to compile due to method name clash with Object Type.
	 * After erasure,generated method will clash with Object.equal methods(existing
	 * methods).Using generics name clashing methods are invalid.
	 */
	/*
	 * public class Twin<T> {
	 * 
	 * public boolean equals(T obj) {
	 * 
	 * return true; } }
	 */
}
