package com.target.myretail.product.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import com.target.myretail.product.model.ProductInfoDTO;
import com.target.myretail.product.model.ProductPriceInfoDTO;
import com.target.myretail.product.model.requestresponse.PriceInfo;
import com.target.myretail.product.model.requestresponse.ProductInfo;

public class DTOConverterTest {

	private ProductInfoDTO createMockProductInfoDTO() {
		ProductInfoDTO productInfoDTO = new ProductInfoDTO();
		productInfoDTO.setId(1);
		productInfoDTO.setName("test_product");
		return productInfoDTO;
	}

	private ProductPriceInfoDTO createMockProductPriceInfoDTO() {
		ProductPriceInfoDTO productPriceInfoDTO = new ProductPriceInfoDTO();
		productPriceInfoDTO.setId(1);
		productPriceInfoDTO.setCurrencyCode("USD");
		productPriceInfoDTO.setPrice(BigDecimal.ONE);
		return productPriceInfoDTO;
	}

	private PriceInfo createMockPriceInfo() {
		PriceInfo priceInfo = new PriceInfo();
		priceInfo.setCurrencyCode("USD");
		priceInfo.setValue(BigDecimal.ONE);
		return priceInfo;
	}

	@Test
	public void testMergeInfos() {
		ProductInfoDTO productInfoDTO = createMockProductInfoDTO();
		ProductPriceInfoDTO productPriceInfoDTO = createMockProductPriceInfoDTO();
		ProductInfo productInfo = DTOConverter.mergeInfos(productInfoDTO, productPriceInfoDTO);
		assertEquals(productInfoDTO.getId(), (long) productInfo.getId());
		assertEquals(productInfoDTO.getName(), productInfo.getName());
		assertEquals(productPriceInfoDTO.getCurrencyCode(), productInfo.getCurrentPrice().getCurrencyCode());
		assertEquals(productPriceInfoDTO.getPrice(), productInfo.getCurrentPrice().getValue());
	}

	@Test
	public void testCreateProductPriceInfoDTO() {
		PriceInfo priceInfo = createMockPriceInfo();
		ProductPriceInfoDTO productPriceInfoDTO = DTOConverter.createPriceInfoDTO(1, priceInfo);
		assertEquals(1, productPriceInfoDTO.getId());
		assertEquals(priceInfo.getCurrencyCode(), productPriceInfoDTO.getCurrencyCode());
		assertEquals(priceInfo.getValue(), productPriceInfoDTO.getPrice());
	}
}
