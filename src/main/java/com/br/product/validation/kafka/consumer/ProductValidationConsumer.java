package com.br.product.validation.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.br.product.validation.service.ProductValidationService;
import com.br.product.validation.utils.JsonUtil;

@Component
public class ProductValidationConsumer {

	private Logger logger = LoggerFactory.getLogger(ProductValidationConsumer.class);
	
	@Autowired
	private ProductValidationService productValidationService;

	@Autowired
	private JsonUtil jsonUtil;
	
	@KafkaListener(
			groupId = "${spring.kafka.consumer.group-id}", 
			topics = "${spring.kafka.topic.product-validation-success}"
	)
	public void consumeSuccessEvent(String payload) {
		
		this.logger.info("Receiving success event {} from notify product-validation-success topic", payload);
		
		var event = jsonUtil.toEvent(payload);
		
		this.logger.info(event.toString());
		
		this.productValidationService.trySuccessEvent(event);
		
	
	}
	
	@KafkaListener(
			groupId = "${spring.kafka.consumer.group-id}", 
			topics = "${spring.kafka.topic.product-validation-fail}"
	)
	public void consumeFailEvent(String payload) {
		
		this.logger.info("Receiving rollback event {} from notify product-validation-fail topic", payload);
		
		var event = jsonUtil.toEvent(payload);
		
		this.logger.info(event.toString());
		
		this.productValidationService.rollbackEvent(event);
	}
	
	
 

}
