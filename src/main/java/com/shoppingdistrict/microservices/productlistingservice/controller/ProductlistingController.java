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
import com.shoppingdistrict.microservices.model.model.Articles;
import com.shoppingdistrict.microservices.model.model.Comment;
import com.shoppingdistrict.microservices.model.model.Orders;
import com.shoppingdistrict.microservices.model.model.Products;
import com.shoppingdistrict.microservices.productlistingservice.configuration.Configuration;
import com.shoppingdistrict.microservices.productlistingservice.repository.ArticleRepository;
import com.shoppingdistrict.microservices.productlistingservice.repository.CommentRepository;
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
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;

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

		logger.info("Returning product {} and exiting from retrieveProduct", product);
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

	@GetMapping("/articles")
	public List<Articles> retriveAllArticles() {
		logger.info("Entry to retriveAllArticles");

		List<Articles> articles = articleRepository.findAll();
		logger.info("Size of all articles", articles.size());

//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId())
//				.toUri();

		logger.info("Returning articles and exiting from retriveAllArticles");

		return articles;

	}
	
	@GetMapping("/articles/{id}")
	public Articles retriveArticleById(@PathVariable Integer id) {
		logger.info("Entry to retriveArticleById");
		
		//	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId())
		//				.toUri();


		Optional<Articles> article = articleRepository.findById(id);
		
		if (article.isEmpty()) {
			logger.info("Article with given id {} not found", id);
			return null;
		} else {
			logger.info("Returning article {} and exiting from retriveArticleById", id);
			 Articles articles = article.get();
			 articles.getComments();
			 return articles;
		}
		
	}
	
	@PostMapping("/articles")
	public ResponseEntity<Articles> createArticle(@Valid @RequestBody Articles article) {
		logger.info("Entry to createArticle");

		logger.info("Article to be created {}", article);
		
		Articles savedArticle = articleRepository.saveAndFlush(article);

//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId())
//				.toUri();

		logger.info("Returning newly created article id {} {} and exiting from createArticle", savedArticle.getId(),
				savedArticle);

		return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);

	}
	
	@PostMapping("/comments")
	public ResponseEntity<Comment> createComment(@Valid @RequestBody Comment comment) {
		logger.info("Entry to createComment");
		logger.info("Comment to be created {}", comment);
		
		Comment savedComment = commentRepository.saveAndFlush(comment);
		
		logger.info("Returning newly created comment id {} {} and exiting from createComment", savedComment.getId(), savedComment);
		return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
	}
	

	
	// TODO: Shall try and catch here or let error handling component to handle
	@PutMapping("/comments/{id}")
	public ResponseEntity<Comment> updateComment(@Valid @RequestBody Comment comment, @PathVariable Integer id) {
		logger.info("Entry to updateComment");

		logger.info("Comment to be updated {}", comment.getId());

		Optional<Comment> existingComment = commentRepository.findById(id);
		existingComment.get().setDescription(comment.getDescription());
		
		Comment updatedComment = commentRepository.saveAndFlush(existingComment.get());

//			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//					.buildAndExpand(updatedProduct.getId()).toUri();

		logger.info("Returning newly updated comment id {} and exiting from updateComment", updatedComment.getId(),
				updatedComment);

		return new ResponseEntity<>(updatedComment,  HttpStatus.CREATED);

	}
	
	
	// TODO: Shall try and catch here or let error handling component to handle
	@PutMapping("/articles/{id}")
	public ResponseEntity<Articles> updateArticle(@Valid @RequestBody Articles article, @PathVariable Integer id) {
		logger.info("Entry to updateArticle");

		logger.info("Article to be updated {}", article.getId());

		Optional<Articles> existingArticles = articleRepository.findById(id);
		existingArticles.get().setCategory(article.getCategory());
		existingArticles.get().setTitle(article.getTitle());
		existingArticles.get().setContent(article.getContent());
		existingArticles.get().setDescription(article.getDescription());
		existingArticles.get().setSubcategory(article.getSubcategory());
		

		Articles updatedArticle = articleRepository.saveAndFlush(existingArticles.get());

//			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//					.buildAndExpand(updatedProduct.getId()).toUri();

		logger.info("Returning newly updated article id {} and exiting from updateArticle", updatedArticle.getId(),
				updatedArticle);

		return new ResponseEntity<>(updatedArticle,  HttpStatus.CREATED);

	}

	

	// TODO: Shall try and catch here or let error handling component to handle
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
