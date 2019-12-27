package com.enableasync.async.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootAsyncApplication {
	public static final int PORT = 8000;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAsyncApplication.class, args);
	}
}
