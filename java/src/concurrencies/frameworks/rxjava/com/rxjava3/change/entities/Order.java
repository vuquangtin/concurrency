package com.rxjava3.change.entities;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a href="https://github.com/vuquangtin/concurrency">https://github.com/
 *      vuquangtin/concurrency</a>
 *
 */
public class Order {
	private int id;
	private String name;

	public Order(String name) {
		try {
			String[] arr = name.split("-");
			setName(arr[0]);
			setId(Integer.parseInt(arr[1]));
		} catch (Exception ex) {
			ex.printStackTrace();
			setName(name);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
