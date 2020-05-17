package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.controller", "com.example.twilio", "com.example.purestake"}) // https://stackoverflow.com/questions/53953272/how-to-fix-http-404-error-during-get-request-in-rest-web-service-using-spring
public class DemoApplication {
	
	public static void main(String[] args) {	
		SpringApplication.run(DemoApplication.class, args);
	}

}
