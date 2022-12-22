package com.shoppingdistrict.microservices.productlistingservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingdistrict.microservices.model.model.Articles;
import com.shoppingdistrict.microservices.model.model.Users;

public interface ArticleRepository extends JpaRepository<Articles, Integer> {
	
	Optional<Articles> findBySubcategory(String subcateogry);

}
