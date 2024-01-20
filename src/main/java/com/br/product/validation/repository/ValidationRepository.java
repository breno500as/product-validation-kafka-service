package com.br.product.validation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.product.validation.model.Validation;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {

	Boolean existsByOrderIdAndTransactionId(String orderId, String transactionId);

	Optional<Validation> findByOrderIdAndTransactionId(String orderId, String transactionId);

}
