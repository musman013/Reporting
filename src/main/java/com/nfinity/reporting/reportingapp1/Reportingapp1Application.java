package com.nfinity.reporting.reportingapp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Reportingapp1Application {

	public static void main(String[] args) {
		SpringApplication.run(Reportingapp1Application.class, args);
	}

}

