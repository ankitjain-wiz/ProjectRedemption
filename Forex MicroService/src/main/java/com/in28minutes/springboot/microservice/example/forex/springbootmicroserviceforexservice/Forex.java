package com.in28minutes.springboot.microservice.example.forex.springbootmicroserviceforexservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Forex {

	public static void main(String[] args) {
		SpringApplication.run(Forex.class, args);
	}

}

