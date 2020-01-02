package com.spring.o7planning.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
@Component
public class MyComponent {

	@Autowired
	private MyRepository myRepository;
	
	public void showApp() {
		System.out.println("Now is: "+ myRepository.getSystemDateTime());
		System.out.println("App name: "+ myRepository.getAppName());
	}
}