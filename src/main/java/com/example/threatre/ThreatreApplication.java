package com.example.threatre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.example.threatre.client")

@SpringBootApplication
public class ThreatreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThreatreApplication.class, args);
	}

}
