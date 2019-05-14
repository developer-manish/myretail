package com.target.myretail.product.model;

import java.io.Serializable;

public class ProductPriceInfoPK implements Serializable {
	private static final long serialVersionUID = -2871009718857901125L;

	private Long id;
	private String currencyCode;

	public ProductPriceInfoPK() {
		super();
	}

	public ProductPriceInfoPK(Long id, String currencyCode) {
		super();
		this.id = id;
		this.currencyCode = currencyCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = (int) (prime * result + id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductPriceInfoPK other = (ProductPriceInfoPK) obj;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

}