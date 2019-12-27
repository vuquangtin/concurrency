package com.enableasync.async.sampleservice;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SampleApplication.class);
		// SpringApplication.run(SampleApplication.class, args);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "8000"));
		app.run(args);
	}
}
