package com.target.myretail.product.service;

import com.target.myretail.product.model.requestresponse.PriceInfo;
import com.target.myretail.product.model.requestresponse.ProductInfo;

public interface ProductInfoService {
	ProductInfo getProductInfo(long id);

	boolean addProductPriceInfo(long id, PriceInfo priceInfo);

	boolean updateProductPriceInfo(long id, PriceInfo priceInfo);
}
