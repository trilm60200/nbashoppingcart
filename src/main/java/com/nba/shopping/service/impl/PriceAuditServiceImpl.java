package com.nba.shopping.service.impl;

import com.nba.shopping.service.PriceAuditService;
import com.nba.shopping.domain.PriceAudit;
import com.nba.shopping.repository.PriceAuditRepository;
import com.nba.shopping.service.dto.PriceAuditDTO;
import com.nba.shopping.service.mapper.PriceAuditMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PriceAudit}.
 */
@Service
@Transactional
public class PriceAuditServiceImpl implements PriceAuditService {

    private final Logger log = LoggerFactory.getLogger(PriceAuditServiceImpl.class);

    private final PriceAuditRepository priceAuditRepository;

    private final PriceAuditMapper priceAuditMapper;

    public PriceAuditServiceImpl(PriceAuditRepository priceAuditRepository, PriceAuditMapper priceAuditMapper) {
        this.priceAuditRepository = priceAuditRepository;
        this.priceAuditMapper = priceAuditMapper;
    }

    /**
     * Save a priceAudit.
     *
     * @param priceAuditDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PriceAuditDTO save(PriceAuditDTO priceAuditDTO) {
        log.debug("Request to save PriceAudit : {}", priceAuditDTO);
        PriceAudit priceAudit = priceAuditMapper.toEntity(priceAuditDTO);
        priceAudit = priceAuditRepository.save(priceAudit);
        return priceAuditMapper.toDto(priceAudit);
    }

    /**
     * Get all the priceAudits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PriceAuditDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PriceAudits");
        return priceAuditRepository.findAll(pageable)
            .map(priceAuditMapper::toDto);
    }

    /**
     * Get one priceAudit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PriceAuditDTO> findOne(Long id) {
        log.debug("Request to get PriceAudit : {}", id);
        return priceAuditRepository.findById(id)
            .map(priceAuditMapper::toDto);
    }

    /**
     * Delete the priceAudit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceAudit : {}", id);
        priceAuditRepository.deleteById(id);
    }
}
