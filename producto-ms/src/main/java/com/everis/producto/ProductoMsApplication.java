package com.everis.producto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.everis.producto.util.CustomRepositoryImpl;

@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
@SpringBootApplication
public class ProductoMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductoMsApplication.class, args);
	}

}
