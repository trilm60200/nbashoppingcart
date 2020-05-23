package com.nba.shopping.web.rest;

import com.nba.shopping.NbaShoppingCartApp;
import com.nba.shopping.domain.PriceAudit;
import com.nba.shopping.repository.PriceAuditRepository;
import com.nba.shopping.service.PriceAuditService;
import com.nba.shopping.service.dto.PriceAuditDTO;
import com.nba.shopping.service.mapper.PriceAuditMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PriceAuditResource} REST controller.
 */
@SpringBootTest(classes = NbaShoppingCartApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PriceAuditResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PriceAuditRepository priceAuditRepository;

    @Autowired
    private PriceAuditMapper priceAuditMapper;

    @Autowired
    private PriceAuditService priceAuditService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPriceAuditMockMvc;

    private PriceAudit priceAudit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceAudit createEntity(EntityManager em) {
        PriceAudit priceAudit = new PriceAudit()
            .productId(DEFAULT_PRODUCT_ID)
            .productName(DEFAULT_PRODUCT_NAME)
            .price(DEFAULT_PRICE)
            .createDate(DEFAULT_CREATE_DATE);
        return priceAudit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceAudit createUpdatedEntity(EntityManager em) {
        PriceAudit priceAudit = new PriceAudit()
            .productId(UPDATED_PRODUCT_ID)
            .productName(UPDATED_PRODUCT_NAME)
            .price(UPDATED_PRICE)
            .createDate(UPDATED_CREATE_DATE);
        return priceAudit;
    }

    @BeforeEach
    public void initTest() {
        priceAudit = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllPriceAudits() throws Exception {
        // Initialize the database
        priceAuditRepository.saveAndFlush(priceAudit);

        // Get all the priceAuditList
        restPriceAuditMockMvc.perform(get("/api/price-audits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPriceAudit() throws Exception {
        // Initialize the database
        priceAuditRepository.saveAndFlush(priceAudit);

        // Get the priceAudit
        restPriceAuditMockMvc.perform(get("/api/price-audits/{id}", priceAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(priceAudit.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPriceAudit() throws Exception {
        // Get the priceAudit
        restPriceAuditMockMvc.perform(get("/api/price-audits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
