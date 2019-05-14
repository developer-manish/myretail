package com.target.myretail.client.redsky;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myretail.product.exception.ProductNotFoundException;
import com.target.myretail.product.model.ProductInfoDTO;

@Repository
public class RedskyRestClientImpl implements RedskyRestClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedskyRestClientImpl.class);
	private static final String EXCLUDES = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

	@Value("${product.info.url}")
	private String productInfoURL;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper infoMapper;

	@Override
	@Cacheable("product-info")
	public ProductInfoDTO getProductInfo(long id) {

		ProductInfoDTO productInfoDTO = new ProductInfoDTO();
		Map<?, ?> infoMap;
		try {
			LOGGER.info("Fetching product details from redsky for product id : " + id);
			ResponseEntity<String> responseEntity = restTemplate.exchange(productInfoURL + "/" + id + EXCLUDES,
					HttpMethod.GET, null, String.class);
			LOGGER.info("Fetched product details from redsky for product id : " + id);
			infoMap = infoMapper.readValue(responseEntity.getBody(), Map.class);
			Map<?, ?> productMap = (Map<?, ?>) infoMap.get("product");
			Map<?, ?> itemMap = (Map<?, ?>) productMap.get("item");
			Map<?, ?> prodDescrMap = (Map<?, ?>) itemMap.get(("product_description"));
			productInfoDTO.setId(id);
			productInfoDTO.setName((String) prodDescrMap.get("title"));
		} catch (JsonParseException | JsonMappingException e) {
			LOGGER.error("Exception in parsing or mapping json.", e);
		} catch (IOException e) {
			LOGGER.error("Exception in coverting to json.", e);
		} catch (RuntimeException e) {
			LOGGER.error("Could not map product info result to product object.", e);
			throw new ProductNotFoundException();
		}
		LOGGER.info("ProductInfoDTO is obtained from redsky for product id : " + id);
		return productInfoDTO;
	}

}
