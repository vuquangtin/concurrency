package com.rxjava3.tutorials;

public class ElementDC {
	private int id;
	private String name;
	public ElementDC(int id, String name) {
		setId(id);
		setName(name);
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
	@Override
	public String toString() {
		return "id:"+id+"\tname:"+name;
	}
}
