package com.bkpasa.actuator;

import javax.validation.ClockProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BcBetsResultedApplication {

	public static void main(String[] args) {
        ClockProvider a;
		SpringApplication.run(BcBetsResultedApplication.class, args);
	}
}
