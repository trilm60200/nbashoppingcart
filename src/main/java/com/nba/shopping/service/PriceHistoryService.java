package com.nba.shopping.service;

import com.nba.shopping.service.dto.PriceHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.nba.shopping.domain.PriceHistory}.
 */
public interface PriceHistoryService {

    /**
     * Save a priceHistory.
     *
     * @param priceHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    PriceHistoryDTO save(PriceHistoryDTO priceHistoryDTO);

    /**
     * Get all the priceHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PriceHistoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" priceHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PriceHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" priceHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
