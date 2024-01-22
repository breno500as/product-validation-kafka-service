package com.br.product.validation.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.product.validation.dtos.Event;
import com.br.product.validation.dtos.History;
import com.br.product.validation.dtos.OrderProducts;
import com.br.product.validation.enums.SagaStatusEnum;
import com.br.product.validation.kafka.producer.OrchestratorProducer;
import com.br.product.validation.model.Validation;
import com.br.product.validation.repository.ProductRepository;
import com.br.product.validation.repository.ValidationRepository;
import com.br.product.validation.utils.JsonUtil;

import jakarta.validation.ValidationException;

@Service
public class ProductValidationService {

	private Logger logger = LoggerFactory.getLogger(ProductValidationService.class);

	private static final String CURRENT_SOURCE = "PRODUCT_VALIDATION_SERVICE";

	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ValidationRepository validationRepository;

	@Autowired
	private OrchestratorProducer orchestratorProducer;

	public void trySuccessEvent(Event event) {
		try {
			this.checkCurrentValidation(event);
			this.createValidation(event, true);
			this.handleSuccess(event);
		} catch (Exception e) {
			this.logger.error("Error trying validate products", e);
			this.handleFailCurrentNotExecuted(event, e.getMessage());
		}

		// Envia para o orquestrador a informação de sucesso para atualização do evento
		this.orchestratorProducer.sendEvent(jsonUtil.toJson(event));
	}

	public void rollbackEvent(Event event) {
		
		changeValidationToFail(event);

		event.setStatus(SagaStatusEnum.FAIL);
		event.setSource(CURRENT_SOURCE);

		this.addHistory(event, "Rollback executed!");
		
		this.orchestratorProducer.sendEvent(jsonUtil.toJson(event));

	}

	private void changeValidationToFail(Event event) {
		this.validationRepository
				.findByOrderIdAndTransactionId(event.getPayload().getId(), event.getTransactionId())
				.ifPresentOrElse(validation -> {
					validation.setSuccess(false);
					this.validationRepository.save(validation);
				}, () -> this.createValidation(event, false));
		;
		
	}

	private void checkCurrentValidation(Event event) {

		if (isEmpty(event.getPayload()) || isEmpty(event.getPayload().getProducts())) {
			throw new ValidationException("Product list empty!");
		}

		if (isEmpty(event.getPayload().getId()) || isEmpty(event.getTransactionId())) {
			throw new ValidationException("Order id and transaction id must be informed!");
		}

		// Faz o tratamento para evitar inconsistência de dados referente ao kafka
		// enviar mais de uma
		/// vez a mesma mensagem
		if (this.validationRepository.existsByOrderIdAndTransactionId(event.getPayload().getId(),
				event.getTransactionId())) {
			throw new ValidationException("There's another transactionId for this validation!");
		}

		event.getPayload().getProducts().forEach(op -> this.validateProduct(op));
	}

	private void validateProduct(OrderProducts op) {

		if (isEmpty(op.getProduct()) || isEmpty(op.getProduct().getCode())) {
			throw new ValidationException("Product must be informed!");
		}

		if (!this.productRepository.existsByCode(op.getProduct().getCode())) {
			throw new ValidationException("Product does not exists in database!");
		}

	}

	private void createValidation(Event event, boolean success) {

		Validation v = new Validation();
		v.setOrderId(event.getPayload().getId());
		v.setTransactionId(event.getTransactionId());
		v.setSuccess(success);

		this.validationRepository.save(v);

	}

	private void handleSuccess(Event event) {

		event.setStatus(SagaStatusEnum.SUCCESS);
		event.setSource(CURRENT_SOURCE);

		this.addHistory(event, "Products validated successfully!");

	}

	private void addHistory(Event event, String message) {

		History h = new History();
		h.setCreatedAt(LocalDateTime.now());
		h.setSource(event.getSource());
		h.setStatus(event.getStatus());
		h.setMessage(message);

		event.addToHistory(h);
	}

	private void handleFailCurrentNotExecuted(Event event, String message) {

		event.setStatus(SagaStatusEnum.ROLLBACK_PENDING);
		event.setSource(CURRENT_SOURCE);

		this.addHistory(event, "Failed to validate products: " + message);

	}

}
