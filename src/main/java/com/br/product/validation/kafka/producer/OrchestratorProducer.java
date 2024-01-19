package com.br.product.validation.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrchestratorProducer {

	private Logger logger = LoggerFactory.getLogger(OrchestratorProducer.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${spring.kafka.topic.orchestrator}")
	private String orchestratorTopic;

	public void sendEvent(String payload) {

		try {

			this.logger.info("Send event to topic {} with data {}", this.orchestratorTopic, payload);
			this.kafkaTemplate.send(this.orchestratorTopic, payload);

		} catch (Exception e) {
			this.logger.error("Error trying to send data {} to topic {}", this.orchestratorTopic, payload, e);
		}

	}

}
