package com.workfolio.workfolio_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

public class WorkfolioBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(WorkfolioBackendApplication.class, args);
	}
}

