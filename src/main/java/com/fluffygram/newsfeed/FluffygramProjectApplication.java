package com.fluffygram.newsfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FluffygramProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FluffygramProjectApplication.class, args);
	}

}
