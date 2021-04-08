package com.slam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //launch a Spring application from a Java main method. 
/*
 * Spring Boot @SpringBootApplication annotation is used to mark a configuration class that declares one or more @Bean methods and also triggers
 * auto-configuration
 * and component scanning. 
 * It’s same as declaring a class
 * with @Configuration, @EnableAutoConfiguration and @ComponentScan annotations.
 */
public class SlamBookApplication { 

	public static void main(String[] args) {
		SpringApplication.run(SlamBookApplication.class, args);
	}
	

}
