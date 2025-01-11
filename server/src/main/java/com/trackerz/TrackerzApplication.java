package com.trackerz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TrackerzApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrackerzApplication.class, args);
	}

}
