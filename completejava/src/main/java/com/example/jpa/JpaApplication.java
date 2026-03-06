package com.example.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaApplication {

	private static final Logger logger = LoggerFactory.getLogger(JpaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			repository.<Customer>save(new Customer("Jack", "Bauer"));
			repository.<Customer>save(new Customer("Chloe", "O'Brian"));
			repository.<Customer>save(new Customer("Kim", "Bauer"));
			repository.<Customer>save(new Customer("David", "Palmer"));
			repository.<Customer>save(new Customer("Michelle", "Dessler"));

			logger.info("Customers found with findAll():");
			repository.findAll().forEach(customer -> {
				logger.info(customer.toString());
			});

			Customer customer = repository.findById(1L);
			logger.info("Customer found with findById(1L):");
			logger.info(customer.toString());

			logger.info("Customer found with findByLastName('Bauer'):");
			repository.findByLastName("Bauer").forEach(bauer -> {
				logger.info(bauer.toString());
			});
		};
	}

}
