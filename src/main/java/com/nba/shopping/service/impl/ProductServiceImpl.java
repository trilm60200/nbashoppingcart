package com.nba.shopping.service.impl;

import com.nba.shopping.domain.PriceAudit;
import com.nba.shopping.repository.PriceAuditRepository;
import com.nba.shopping.service.ProductService;
import com.nba.shopping.domain.Product;
import com.nba.shopping.repository.ProductRepository;
import com.nba.shopping.service.dto.ProductDTO;
import com.nba.shopping.service.mapper.ProductMapper;
import org.h2.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final PriceAuditRepository priceAuditRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, PriceAuditRepository priceAuditRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.priceAuditRepository = priceAuditRepository;
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(productDTO.getName());
        stringBuilder.append(productDTO.getBranch());
        product.setSearch(stringBuilder.toString().toLowerCase());
        product = productRepository.save(product);

        List<PriceAudit> priceAudits = priceAuditRepository.findByProductId(productDTO.getId());
        if (!priceAudits.isEmpty()) {
            PriceAudit priceAudit = priceAudits.get(0);
            if (!priceAudit.getPrice().toString().equals(productDTO.getPrice())) {
                priceAuditRepository.save(this.preparePriceAudit(product));
            }
        } else {
            priceAuditRepository.save(this.preparePriceAudit(product));
        }

        return productMapper.toDto(product);
    }

    /**
     * Get all the products.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable)
            .map(productMapper::toDto);
    }

    /**
     * Get one product by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id)
            .map(productMapper::toDto);
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }


    /**
     * Get all by search key.
     *
     * @param key the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findBySearch(String key) {
        log.debug("Request to get all Products");
        if (StringUtils.isNullOrEmpty(key))
            return null;
        return productMapper.toDto(productRepository.findBySearchContainingIgnoreCase(key.toLowerCase()));
    }

    private PriceAudit preparePriceAudit(Product productDTO) {
        PriceAudit priceAudit = new PriceAudit();
        priceAudit.setProductId(productDTO.getId());
        priceAudit.setProductName(productDTO.getName());
        priceAudit.setPrice(Integer.valueOf(productDTO.getPrice()));
        priceAudit.setCreateDate(LocalDate.now());

        return priceAudit;
    }
}
