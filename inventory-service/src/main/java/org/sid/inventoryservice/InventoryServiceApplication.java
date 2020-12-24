package org.sid.inventoryservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(ProductRepository productRepository,RepositoryRestConfiguration restConfiguration){
		restConfiguration.exposeIdsFor(Product.class);
	return args -> {
	productRepository.save(new Product(null,"Computer Desk Top HP",900,10));
	productRepository.save(new Product(null,"Printer Epson",80,11));
	productRepository.save(new Product(null,"MacBook Pro Lap Top",1800,5));
	productRepository.findAll().forEach(System.out::println);
	};
	}
}
	@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString
	class Product{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; private String name;private double price;private int quantity;
	}
	@RepositoryRestResource
	interface ProductRepository extends JpaRepository<Product,Long> { }
	
	
	
	
	
	
	
	
	
	
	
	
	
