package com.br.product.validation.dtos;

import java.time.LocalDateTime;

import com.br.product.validation.enums.SagaStatusEnum;

public class History {

	private String source;

	private SagaStatusEnum status;

	private String message;

	private LocalDateTime createdAt;

	public History() {

	}

	public History(String source, SagaStatusEnum status, String message, LocalDateTime createdAt) {
		super();
		this.source = source;
		this.status = status;
		this.message = message;
		this.createdAt = createdAt;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public SagaStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SagaStatusEnum status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
