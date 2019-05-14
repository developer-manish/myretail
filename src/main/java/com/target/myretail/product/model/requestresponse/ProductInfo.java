package com.target.myretail.product.model.requestresponse;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductInfo {
	@NotNull
	@Min(1)
	private Long id;
	private String name;
	@Valid
	@JsonProperty("current_price")
	private PriceInfo currentPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PriceInfo getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(PriceInfo currentPrice) {
		this.currentPrice = currentPrice;
	}

}
