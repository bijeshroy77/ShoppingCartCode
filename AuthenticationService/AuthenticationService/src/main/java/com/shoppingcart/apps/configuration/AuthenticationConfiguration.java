package com.shoppingcart.apps.configuration;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.shoppingcart.apps.repository")
@EntityScan(basePackages = "com.shoppingcart.apps.entity")
@ComponentScan("com.shoppingcart.apps")

public class AuthenticationConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationConfiguration.class, args);
	}

}
