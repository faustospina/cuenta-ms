package com.cuenta.cuenta_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CuentaMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuentaMsApplication.class, args);
	}

}
