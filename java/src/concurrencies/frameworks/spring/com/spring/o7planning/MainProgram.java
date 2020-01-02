package com.spring.o7planning;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.spring.o7planning.bean.GreetingService;
import com.spring.o7planning.bean.MyComponent;
import com.spring.o7planning.config.AppConfiguration;
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
public class MainProgram {
	public static void main(String[] args) {
		System.out.println();
		ApplicationContext context = new AnnotationConfigApplicationContext(
				AppConfiguration.class);

		System.out.println("---------------");
		Language language = (Language) context.getBean("language");

		System.out.println("Bean Language: " + language);
		System.out.println(("CallLanguage.getBye(): ") + language.getBye());

		System.out.println("----------");

		GreetingService service = (GreetingService) context
				.getBean("greetingService");

		service.sayGreeting();

		System.out.println("----------");

		MyComponent myComponent = (MyComponent) context.getBean("myComponent");

		myComponent.showApp();

		System.out.println("ManhNT");
	}

}