package com.hystrix.async.spring.apps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


//@EnableEurekaClient
//@EnableCircuitBreaker
@SpringBootApplication
public class CompositeQuestionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompositeQuestionServiceApplication.class, args);
	}
}