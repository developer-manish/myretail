package com.target.myretail.client.redsky;

import com.target.myretail.product.model.ProductInfoDTO;

public interface RedskyRestClient {
	/**
	 * throws ProductNotFoundException in case product is not available with input
	 * id.
	 * 
	 * @param id
	 * @return ProductInfoDTO
	 */
	ProductInfoDTO getProductInfo(long id);
}
