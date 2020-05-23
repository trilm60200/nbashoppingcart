package com.nba.shopping.service;

import com.nba.shopping.service.dto.PriceAuditDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.nba.shopping.domain.PriceAudit}.
 */
public interface PriceAuditService {

    /**
     * Save a priceAudit.
     *
     * @param priceAuditDTO the entity to save.
     * @return the persisted entity.
     */
    PriceAuditDTO save(PriceAuditDTO priceAuditDTO);

    /**
     * Get all the priceAudits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PriceAuditDTO> findAll(Pageable pageable);

    /**
     * Get the "id" priceAudit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PriceAuditDTO> findOne(Long id);

    /**
     * Delete the "id" priceAudit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
