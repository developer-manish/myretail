package com.target.myretail.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.target.myretail.product.model.ProductPriceInfoDTO;
import com.target.myretail.product.model.ProductPriceInfoPK;

public interface ProductPriceRepository extends JpaRepository<ProductPriceInfoDTO, ProductPriceInfoPK> {
	@Modifying
	@Query("update ProductPriceInfoDTO p set p.price = :#{#productPriceInfoDTO.price}")
	int updateProductPriceInfo(ProductPriceInfoDTO productPriceInfoDTO);
}
