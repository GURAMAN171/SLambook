package com.slam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SlamBookApplication { 
/*public class SlamBookApplication extends SpringBootServletInitializer {*/

	public static void main(String[] args) {
		SpringApplication.run(SlamBookApplication.class, args);
	}
	/*
	 * changes done
	 * 
	 * @Override protected SpringApplicationBuilder
	 * configure(SpringApplicationBuilder builder) {
	 * 
	 * return builder.sources(SlamBookApplication.class); } changes done
	 */

}
