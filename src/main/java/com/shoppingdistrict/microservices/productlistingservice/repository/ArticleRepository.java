package com.shoppingdistrict.microservices.productlistingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingdistrict.microservices.model.model.Articles;

public interface ArticleRepository extends JpaRepository<Articles, Integer> {

}
