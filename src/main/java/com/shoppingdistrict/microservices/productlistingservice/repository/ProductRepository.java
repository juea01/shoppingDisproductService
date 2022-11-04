package com.shoppingdistrict.microservices.productlistingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingdistrict.microservices.model.model.Products;

public interface ProductRepository extends JpaRepository<Products, Integer> {

}
