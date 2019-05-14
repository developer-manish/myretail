package com.target.myretail.product.model.requestresponse;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceInfo {
	@JsonProperty("currency_code")
	@NotNull
	private String currencyCode;
	@JsonProperty("value")
	@DecimalMin("0.01")
	private BigDecimal value;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
