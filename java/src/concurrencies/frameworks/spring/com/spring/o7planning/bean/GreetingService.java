package com.spring.o7planning.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.o7planning.lang.Language;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
@Service
public class GreetingService {

	@Autowired
	private Language language;
	
	public GreetingService() {
		// TODO Auto-generated constructor stub
	}
	
	public void sayGreeting(){
		String greeting = language.getGreeting();
		
		System.out.println("Greeting: "+greeting);
	}
	int a;
}