package com.target.myretail.product.util;

import com.target.myretail.product.model.ProductInfoDTO;
import com.target.myretail.product.model.ProductPriceInfoDTO;
import com.target.myretail.product.model.requestresponse.PriceInfo;
import com.target.myretail.product.model.requestresponse.ProductInfo;

public class DTOConverter {

	public static ProductInfo mergeInfos(ProductInfoDTO productInfoDTO, ProductPriceInfoDTO productPriceInfoDTO) {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setId(productInfoDTO.getId());
		productInfo.setName(productInfoDTO.getName());
		PriceInfo priceInfo = new PriceInfo();
		priceInfo.setValue(productPriceInfoDTO.getPrice());
		priceInfo.setCurrencyCode(productPriceInfoDTO.getCurrencyCode());
		productInfo.setCurrentPrice(priceInfo);
		return productInfo;

	}

	public static ProductPriceInfoDTO createPriceInfoDTO(long id, PriceInfo priceInfo) {
		ProductPriceInfoDTO productPriceInfoDTO = new ProductPriceInfoDTO();
		productPriceInfoDTO.setId(id);
		productPriceInfoDTO.setCurrencyCode(priceInfo.getCurrencyCode());
		productPriceInfoDTO.setPrice(priceInfo.getValue());
		return productPriceInfoDTO;
	}
}
