package com.target.myretail;

import java.math.BigDecimal;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.target.myretail.product.model.ProductPriceInfoPK;
import com.target.myretail.product.model.requestresponse.PriceInfo;
import com.target.myretail.product.repository.ProductPriceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyRetailApplicationTests {
	@Autowired
	private Environment environment;
	private String port;

	@Autowired
	private ProductPriceRepository productPriceRepository;

	@Before
	public void setup() {
		port = environment.getProperty("local.server.port");
	}

	private PriceInfo createPriceInfo() {
		PriceInfo priceInfo = new PriceInfo();
		priceInfo.setCurrencyCode("USD");
		priceInfo.setValue(BigDecimal.ONE);
		return priceInfo;
	}

	@Test
	public void testGetProductInfo() throws JSONException {

		String response = new RestTemplate().getForObject("http://localhost:" + port + "/myretail/v1/products/13860427",
				String.class);
		JSONAssert.assertEquals(
				"{\"id\":13860427,\"name\":\"Conan the Barbarian (dvd_video)\",\"current_price\":{\"currency_code\":\"USD\",\"value\":1}}",
				response, true);
	}

	@Test
	public void testAddProductPriceInfo() throws JSONException {

		ProductPriceInfoPK id = new ProductPriceInfoPK(13860424L, "USD");
		if (productPriceRepository.existsById(id)) {
			productPriceRepository.deleteById(id);
		}
		PriceInfo priceInfo = createPriceInfo();
		ResponseEntity<String> response = new RestTemplate().exchange(
				"http://localhost:" + port + "/myretail/v1/products/13860424", HttpMethod.POST,
				new HttpEntity<PriceInfo>(priceInfo), String.class);
		JSONAssert.assertEquals("{\"code\": 201, \"message\": \"Product price details are added.\"}",
				response.getBody(), true);
	}

	@Test
	public void testUpdateProductPriceInfo() throws JSONException {

		ResponseEntity<String> response = new RestTemplate().exchange(
				"http://localhost:" + port + "/myretail/v1/products/13860427", HttpMethod.PUT,
				new HttpEntity<PriceInfo>(createPriceInfo()), String.class);
		JSONAssert.assertEquals("{\"code\": 200, \"message\": \"Product price details are updated.\"}",
				response.getBody(), true);
	}

}
