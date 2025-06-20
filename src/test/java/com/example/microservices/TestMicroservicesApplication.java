package com.example.microservices;

import org.springframework.boot.SpringApplication;

public class TestMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.from(MicroservicesApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
