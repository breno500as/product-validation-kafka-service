package com.br.product.validation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.product.validation.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	Boolean existsByCode(String code);

}
