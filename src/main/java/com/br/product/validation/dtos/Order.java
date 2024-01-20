package com.br.product.validation.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

	private String id;

	private List<OrderProducts> products;

	private LocalDateTime createdAt;

	private String transactionId;

	private double totalAmount;

	private int totalItens;

	public Order() {

	}

	public Order(String id, List<OrderProducts> products, LocalDateTime createdAt, String transactionId,
			double totalAmount, int totalItens) {
		super();
		this.id = id;
		this.products = products;
		this.createdAt = createdAt;
		this.transactionId = transactionId;
		this.totalAmount = totalAmount;
		this.totalItens = totalItens;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<OrderProducts> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProducts> products) {
		this.products = products;
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
