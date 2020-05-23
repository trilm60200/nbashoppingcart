package com.nba.shopping.repository;

import com.nba.shopping.domain.PriceAudit;

import com.nba.shopping.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the PriceAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceAuditRepository extends JpaRepository<PriceAudit, Long> {

    @Query("select priceAudit from PriceAudit priceAudit where priceAudit.productId = ?1 order by priceAudit.createDate DESC")
    List<PriceAudit> findByProductId(@Param("productId") Long productId);

}
