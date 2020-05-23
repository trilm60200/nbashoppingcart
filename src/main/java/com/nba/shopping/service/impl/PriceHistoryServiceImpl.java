package com.nba.shopping.service.impl;

import com.nba.shopping.service.PriceHistoryService;
import com.nba.shopping.domain.PriceHistory;
import com.nba.shopping.repository.PriceHistoryRepository;
import com.nba.shopping.service.dto.PriceHistoryDTO;
import com.nba.shopping.service.mapper.PriceHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PriceHistory}.
 */
@Service
@Transactional
public class PriceHistoryServiceImpl implements PriceHistoryService {

    private final Logger log = LoggerFactory.getLogger(PriceHistoryServiceImpl.class);

    private final PriceHistoryRepository priceHistoryRepository;

    private final PriceHistoryMapper priceHistoryMapper;

    public PriceHistoryServiceImpl(PriceHistoryRepository priceHistoryRepository, PriceHistoryMapper priceHistoryMapper) {
        this.priceHistoryRepository = priceHistoryRepository;
        this.priceHistoryMapper = priceHistoryMapper;
    }

    /**
     * Save a priceHistory.
     *
     * @param priceHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PriceHistoryDTO save(PriceHistoryDTO priceHistoryDTO) {
        log.debug("Request to save PriceHistory : {}", priceHistoryDTO);
        PriceHistory priceHistory = priceHistoryMapper.toEntity(priceHistoryDTO);
        priceHistory = priceHistoryRepository.save(priceHistory);
        return priceHistoryMapper.toDto(priceHistory);
    }

    /**
     * Get all the priceHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PriceHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PriceHistories");
        return priceHistoryRepository.findAll(pageable)
            .map(priceHistoryMapper::toDto);
    }

    /**
     * Get one priceHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PriceHistoryDTO> findOne(Long id) {
        log.debug("Request to get PriceHistory : {}", id);
        return priceHistoryRepository.findById(id)
            .map(priceHistoryMapper::toDto);
    }

    /**
     * Delete the priceHistory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceHistory : {}", id);
        priceHistoryRepository.deleteById(id);
    }
}
