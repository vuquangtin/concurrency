package com.hystrix.async.spring.models;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Movie {
	int id;
	String title;
	String friend;
	int rating;

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getFriend() {
		return friend;
	}

	public int getRating() {
		return rating;
	}

	public void setFriend(String friend) {
		this.friend = friend;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}