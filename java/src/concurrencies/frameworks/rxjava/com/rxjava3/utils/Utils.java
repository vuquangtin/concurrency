package com.rxjava3.utils;

import java.util.List;

import org.springframework.web.client.RestTemplate;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Utils {
	static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String numberToAlphabet(long x) {
		return Character.toString(ALPHABET.charAt((int) x % ALPHABET.length()));

	}

	public static void println(String s) {
		System.out.println(s);
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sleep10000() {
		sleep(10000);
	}

	public static String invoke(String URI) {
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(URI, String.class);
		return result;
	}

	public static void hold(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class Course {
	private String name;// 课程名
	private String id;

	public Course(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class Student {
	String name;
	private List<Course> coursesList;// 所修的课程

	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Course> getCoursesList() {
		return coursesList;
	}

	public void setCoursesList(List<Course> coursesList) {
		this.coursesList = coursesList;
	}
}