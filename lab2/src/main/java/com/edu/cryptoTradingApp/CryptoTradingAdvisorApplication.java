package com.edu.cryptoTradingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptoTradingAdvisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoTradingAdvisorApplication.class, args);
	}

}

