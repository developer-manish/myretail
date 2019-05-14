package com.target.myretail.product.resource;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.target.myretail.product.exception.ProductNotFoundException;
import com.target.myretail.product.exception.ProductPriceNotFoundException;
import com.target.myretail.product.model.requestresponse.GenericMessageResponse;
import com.target.myretail.product.model.requestresponse.PriceInfo;
import com.target.myretail.product.model.requestresponse.ProductInfo;
import com.target.myretail.product.service.ProductInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "myRetail" })
@Validated
@RestController
@RequestMapping("/myretail")
public class ProductInfoResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoResource.class);

	@Value("${bad.request.message}")
	private String badRequestMessage;

	@Value("${product.not.found.message}")
	private String productNotFoundMessage;

	@Value("${product.price.not.found.message}")
	private String productPriceNotFoundMessage;

	@Value("${product.price.added.message}")
	private String productPriceAddedMessage;

	@Value("${product.price.not.added.message}")
	private String productPriceNotAddedMessage;

	@Value("${product.price.updated.message}")
	private String productPriceUpdatedMessage;

	@Autowired
	private ProductInfoService productInfoService;

	@ApiOperation(value = "Fetches product and price information by product id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Product is not found") })
	@GetMapping(path = "v1/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductInfo getProductInfo(@PathVariable("id") @NotNull @Min(1) Long id) {
		LOGGER.info("Getting product details for product id : " + id);
		return productInfoService.getProductInfo(id);
	}

	@ApiOperation(value = "Adds price information for product id")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Product price details are added."),
			@ApiResponse(code = 404, message = "Product is not found") })
	@PostMapping(path = "v1/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public GenericMessageResponse addProductPriceInfo(@PathVariable("id") @NotNull Long id,
			@RequestBody @Valid PriceInfo priceInfo) {
		LOGGER.info("Adding price details for product id : " + id);
		boolean isAdded = productInfoService.addProductPriceInfo(id, priceInfo);
		GenericMessageResponse genericMessageResponse = isAdded
				? new GenericMessageResponse(201, productPriceAddedMessage)
				: new GenericMessageResponse(201, productPriceNotAddedMessage);
		return genericMessageResponse;
	}

	@ApiOperation(value = "Updates price for product id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Product price details are not found") })
	@PutMapping(path = "v1/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public GenericMessageResponse updateProductPriceInfo(@PathVariable("id") @NotNull Long id,
			@RequestBody @Valid PriceInfo priceInfo) {
		LOGGER.info("Adding product details for product id : " + id);
		productInfoService.updateProductPriceInfo(id, priceInfo);
		return new GenericMessageResponse(200, productPriceUpdatedMessage);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
	public ResponseEntity<GenericMessageResponse> handleMethodArgumentNotValidException() {
		GenericMessageResponse genericMessageResponse = new GenericMessageResponse(400, badRequestMessage);
		ResponseEntity<GenericMessageResponse> responseEntity = new ResponseEntity<>(genericMessageResponse,
				HttpStatus.BAD_REQUEST);
		return responseEntity;
	}

	@ExceptionHandler({ ProductNotFoundException.class })
	public ResponseEntity<GenericMessageResponse> handleProductNotFoundException() {
		GenericMessageResponse genericMessageResponse = new GenericMessageResponse(404, productNotFoundMessage);
		ResponseEntity<GenericMessageResponse> responseEntity = new ResponseEntity<>(genericMessageResponse,
				HttpStatus.NOT_FOUND);
		return responseEntity;
	}

	@ExceptionHandler({ ProductPriceNotFoundException.class })
	public ResponseEntity<GenericMessageResponse> handleProductPriceNotFoundException() {
		GenericMessageResponse genericMessageResponse = new GenericMessageResponse(404, productPriceNotFoundMessage);
		ResponseEntity<GenericMessageResponse> responseEntity = new ResponseEntity<>(genericMessageResponse,
				HttpStatus.NOT_FOUND);
		return responseEntity;
	}

}
