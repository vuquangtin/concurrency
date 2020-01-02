package com.hystrix.async.spring.models;

import lombok.Data;

import org.springframework.data.annotation.Id;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
@Data
public class Chapter {
	@Id
	private String id;
	private String name;

	public Chapter(String name) {
		this.name = name;
	}
}