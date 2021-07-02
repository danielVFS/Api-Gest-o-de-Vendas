package com.daniel.gvendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.daniel.gvendas.entities"})
@EnableJpaRepositories(basePackages = {"com.daniel.gvendas.repository"})
@ComponentScan(basePackages = {"com.daniel.gvendas.services", "com.daniel.gvendas.controllers"})
@SpringBootApplication
public class GvendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GvendasApplication.class, args);
	}

}
