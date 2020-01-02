package com.hystrix.async.spring.apps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The @SpringBootApplication annotation tells Spring Boot, when launched, to
 * scan recursively for Spring components inside this package and register them.
 * It also tells Spring Boot to enable autoconfiguration, a process where beans
 * are automatically created based on classpath settings, property settings, and
 * other factors. We'll see more of this throughout the book. Finally, it
 * indicates that this class itself can be a source for Spring bean definitions. <br/>
 * It holds public static void main() , a simple method to run the application.
 * There is no need to drop this code into an application server or servlet
 * container. We can just run it straight up, inside our IDE. The amount of time
 * saved by this feature, over the long haul, adds up fast. <br/>
 * SpringApplication.run() points Spring Boot at the leap-off point--​ in this
 * case, this very class. But it's possible to run other classes.
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */

@SpringBootApplication
public class LearningSpringBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearningSpringBootApplication.class, args);
	}
}
