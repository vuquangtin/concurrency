package com.spring.o7planning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.spring.o7planning.lang.Language;
import com.spring.o7planning.lang.impl.Chinese;
import com.spring.o7planning.lang.impl.English;

/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
@Configuration
@ComponentScan({ "com.spring.o7planning.bean" })
public class AppConfiguration {
	@Bean(name = "language")
	public Language getLanguage() {
		return new English();
	}
	
	@Bean(name = "language1")
	public Language getLanguage1(){
		return new Chinese();
	}
}
