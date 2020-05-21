package com.nba.shopping.repository;

import com.nba.shopping.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("select product from Product product where product.search like %?1%")
    List<Product> findBySearchContainingIgnoreCase(@Param("query") String search);

}
