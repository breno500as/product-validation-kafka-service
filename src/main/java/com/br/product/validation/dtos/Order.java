package com.br.product.validation.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

	private Long id;

	private List<OrderProducts> product;

	private LocalDateTime createdAt;

	private String transactionId;

	private double totalAmount;

	private int totalItens;

	public Order() {

	}

	public Order(Long id, List<OrderProducts> product, LocalDateTime createdAt, String transactionId,
			double totalAmount, int totalItens) {
		super();
		this.id = id;
		this.product = product;
		this.createdAt = createdAt;
		this.transactionId = transactionId;
		this.totalAmount = totalAmount;
		this.totalItens = totalItens;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<OrderProducts> getProduct() {
		return product;
	}

	public void setProduct(List<OrderProducts> product) {
		this.product = product;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalItens() {
		return totalItens;
	}

	public void setTotalItens(int totalItens) {
		this.totalItens = totalItens;
	}

}
