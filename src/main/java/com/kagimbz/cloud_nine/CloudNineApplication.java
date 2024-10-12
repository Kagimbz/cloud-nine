package com.kagimbz.cloud_nine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CloudNineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudNineApplication.class, args);
	}

}
