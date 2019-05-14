package com.target.myretail.product.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.target.myretail.client.redsky.RedskyRestClient;
import com.target.myretail.product.exception.ProductPriceNotFoundException;
import com.target.myretail.product.model.ProductInfoDTO;
import com.target.myretail.product.model.ProductPriceInfoDTO;
import com.target.myretail.product.model.ProductPriceInfoPK;
import com.target.myretail.product.model.requestresponse.PriceInfo;
import com.target.myretail.product.model.requestresponse.ProductInfo;
import com.target.myretail.product.repository.ProductPriceRepository;
import com.target.myretail.product.util.DTOConverter;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoServiceImpl.class);

	@Autowired
	private RedskyRestClient redskyRestClient;

	@Autowired
	private ProductPriceRepository productPriceRepository;

	@Override
	public ProductInfo getProductInfo(long id) {
		LOGGER.info("Fetching product details for product id : " + id);
		ProductInfoDTO productInfoDTO = redskyRestClient.getProductInfo(id);
		Optional<ProductPriceInfoDTO> optionalProductPriceInfoDTO = productPriceRepository
				.findById(new ProductPriceInfoPK(id, "USD"));
		ProductPriceInfoDTO productPriceInfoDTO = optionalProductPriceInfoDTO.isPresent() == true
				? optionalProductPriceInfoDTO.get()
				: new ProductPriceInfoDTO();
		ProductInfo productInfo = DTOConverter.mergeInfos(productInfoDTO, productPriceInfoDTO);
		LOGGER.info("Fetched product details for product id : " + id);
		return productInfo;
	}

	@Override
	@Transactional
	public boolean addProductPriceInfo(long id, PriceInfo priceInfo) {
		LOGGER.info("Fetching product details for product id : " + id);
		/*
		 * to check if the product is present, throws ProductNotFoundException in case
		 * of product is'nt available in redsky.
		 */
		redskyRestClient.getProductInfo(id);
		ProductPriceInfoDTO productPriceInfoDTO = DTOConverter.createPriceInfoDTO(id, priceInfo);
		if (!productPriceRepository.existsById(new ProductPriceInfoPK(productPriceInfoDTO.getId(), "USD"))) {
			productPriceInfoDTO = productPriceRepository.save(productPriceInfoDTO);
			LOGGER.info("Added product price info for product id : " + productPriceInfoDTO.getId());
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateProductPriceInfo(long id, PriceInfo priceInfo) {
		LOGGER.info("Fetching product details for product id : " + id);
		/*
		 * to check if the product is present, throws ProductNotFoundException in case
		 * of product is'nt available in redsky.
		 */
		redskyRestClient.getProductInfo(id);
		ProductPriceInfoDTO productPriceInfoDTO = DTOConverter.createPriceInfoDTO(id, priceInfo);
		int updated = 0;
		if (productPriceRepository.existsById(new ProductPriceInfoPK(productPriceInfoDTO.getId(), "USD"))) {
			updated = productPriceRepository.updateProductPriceInfo(productPriceInfoDTO);
		} else {
			throw new ProductPriceNotFoundException();
		}
		LOGGER.info("Updated product price info for product id : " + productPriceInfoDTO.getId());
		return updated == 0 ? false : true;
	}

}
