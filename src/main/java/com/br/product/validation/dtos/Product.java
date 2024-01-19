package com.br.product.validation.dtos;

public class Product {

	private String code;

	private double unitValue;

	public Product() {

	}

	public Product(String code, double unitValue) {
		super();
		this.code = code;
		this.unitValue = unitValue;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(double unitValue) {
		this.unitValue = unitValue;
	}

}
