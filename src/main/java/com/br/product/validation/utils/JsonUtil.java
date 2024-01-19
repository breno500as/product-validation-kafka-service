package com.br.product.validation.utils;

 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.br.product.validation.dtos.Event;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonUtil {

	private final ObjectMapper mapper;
	
	private Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public JsonUtil(ObjectMapper mapper) {
		this.mapper = mapper;

	}

	public String toJson(Object objet) {
		try {
			return this.mapper.writeValueAsString(objet);
		} catch (Exception e) {
			this.logger.error(e.getMessage(),e);
			return null;
		}
	}

	public Event toEvent(String json) {
		try {
			return this.mapper.readValue(json, Event.class);
		} catch (Exception e) {
			this.logger.error(e.getMessage(),e);
			return null;
		}
	}

}
