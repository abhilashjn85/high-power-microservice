package com.practice.highpower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableMongoAuditing
@EnableWebFlux
public class HighPowerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HighPowerMicroserviceApplication.class, args);
	}
}
