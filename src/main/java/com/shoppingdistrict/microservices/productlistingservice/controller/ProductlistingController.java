package com.shoppingdistrict.microservices.productlistingservice.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shoppingdistrict.microservices.model.model.Users;
import com.shoppingdistrict.microservices.model.model.Orders;
import com.shoppingdistrict.microservices.model.model.Products;
import com.shoppingdistrict.microservices.productlistingservice.configuration.Configuration;
import com.shoppingdistrict.microservices.productlistingservice.repository.ProductRepository;

@RestController
@RequestMapping("/product-listing-service")
public class ProductlistingController {

	private Logger logger = LoggerFactory.getLogger(ProductlistingController.class);

	@Autowired
	private Environment environment;

	@Autowired
	private ProductRepository repository;

	@Autowired
	private Configuration configuration;

	// retrieveOrder
	@GetMapping("/products/{id}")
	public Products retrieveProduct(@PathVariable Integer id) {
		logger.info("Entry to retrieveProduct");

//		logger.info("Port used {}", environment.getProperty("local.server.port"));
//		logger.info("Minimum from configuration {}", configuration.getMinimum());
//		

		Products product = repository.findById(id).get();

		logger.info("Returning user {} and exiting from retrieveProduct", product);
		return product;
	}

	@PostMapping("/products")
	public ResponseEntity<Products> createProduct(@Valid @RequestBody Products product) {
		logger.info("Entry to createProduct");

		logger.info("Product to be created {}", product);
		Products savedProduct = repository.saveAndFlush(product);

//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId())
//				.toUri();

		logger.info("Returning newly created product id {} {} and exiting from createProduct", savedProduct.getId(),
				savedProduct);

		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
				

	}
	
	//TODO: Shall try and catch here or let error handling component to handle
		@PutMapping("/products/{id}")
		public ResponseEntity<Products> updateProduct(@Valid @RequestBody Products product, @PathVariable Integer id) {
			logger.info("Entry to updateProduct");

			logger.info("Product to be updated {}", product.getId());
			
			Optional<Products> existingProduct = repository.findById(id);
			existingProduct.get().setCategory(product.getCategory());
			existingProduct.get().setName(product.getName());
			existingProduct.get().setDescription(product.getDescription());
			existingProduct.get().setPrice(product.getPrice());
			
			
			Products updatedProduct = repository.saveAndFlush(existingProduct.get());
			

//			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//					.buildAndExpand(updatedProduct.getId()).toUri();

			logger.info("Returning newly updated product id {} and exiting from updateProduct", updatedProduct.getId(),
					updatedProduct);

			return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);

		}

	
	@GetMapping("/products")
	public List<Products> retrieveAllProducts() {
		logger.info("Entry to retrieveAllProducts");
		List<Products> products = repository.findAll();
		logger.info("Size of all orders", products.size());
		logger.info("Returning orders and exiting from retrieveAllProducts");
		return products;
	}

}
