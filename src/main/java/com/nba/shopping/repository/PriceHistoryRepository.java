package com.nba.shopping.repository;

import com.nba.shopping.domain.PriceHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PriceHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
}
