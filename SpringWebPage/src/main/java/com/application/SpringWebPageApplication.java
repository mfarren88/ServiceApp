package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"com.application"})
@EnableAutoConfiguration
public class SpringWebPageApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebPageApplication.class, args);
	}
}
