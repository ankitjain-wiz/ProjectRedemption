package com.micro.stack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class MicroserviceStackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceStackApplication.class, args);
	}

}
