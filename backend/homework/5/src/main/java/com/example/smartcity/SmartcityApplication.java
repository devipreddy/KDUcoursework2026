package com.example.smartcity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class SmartcityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartcityApplication.class, args);
	}

}
