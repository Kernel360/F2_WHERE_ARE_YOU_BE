package org.badminton.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BadmintonApplication {

	public static void main(String[] args) {
		SpringApplication.run(BadmintonApplication.class, args);
	}

}

