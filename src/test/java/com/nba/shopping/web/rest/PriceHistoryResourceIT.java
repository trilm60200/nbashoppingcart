package com.nba.shopping.web.rest;

import com.nba.shopping.NbaShoppingCartApp;
import com.nba.shopping.domain.PriceHistory;
import com.nba.shopping.repository.PriceHistoryRepository;
import com.nba.shopping.service.PriceHistoryService;
import com.nba.shopping.service.dto.PriceHistoryDTO;
import com.nba.shopping.service.mapper.PriceHistoryMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PriceHistoryResource} REST controller.
 */
@SpringBootTest(classes = NbaShoppingCartApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PriceHistoryResourceIT {

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private PriceHistoryMapper priceHistoryMapper;

    @Autowired
    private PriceHistoryService priceHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPriceHistoryMockMvc;

    private PriceHistory priceHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceHistory createEntity(EntityManager em) {
        PriceHistory priceHistory = new PriceHistory()
            .price(DEFAULT_PRICE);
        return priceHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceHistory createUpdatedEntity(EntityManager em) {
        PriceHistory priceHistory = new PriceHistory()
            .price(UPDATED_PRICE);
        return priceHistory;
    }

    @BeforeEach
    public void initTest() {
        priceHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriceHistory() throws Exception {
        int databaseSizeBeforeCreate = priceHistoryRepository.findAll().size();

        // Create the PriceHistory
        PriceHistoryDTO priceHistoryDTO = priceHistoryMapper.toDto(priceHistory);
        restPriceHistoryMockMvc.perform(post("/api/price-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(priceHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the PriceHistory in the database
        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        PriceHistory testPriceHistory = priceHistoryList.get(priceHistoryList.size() - 1);
        assertThat(testPriceHistory.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createPriceHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceHistoryRepository.findAll().size();

        // Create the PriceHistory with an existing ID
        priceHistory.setId(1L);
        PriceHistoryDTO priceHistoryDTO = priceHistoryMapper.toDto(priceHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceHistoryMockMvc.perform(post("/api/price-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(priceHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceHistory in the database
        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPriceHistories() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);

        // Get all the priceHistoryList
        restPriceHistoryMockMvc.perform(get("/api/price-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }
    
    @Test
    @Transactional
    public void getPriceHistory() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);

        // Get the priceHistory
        restPriceHistoryMockMvc.perform(get("/api/price-histories/{id}", priceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(priceHistory.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }

    @Test
    @Transactional
    public void getNonExistingPriceHistory() throws Exception {
        // Get the priceHistory
        restPriceHistoryMockMvc.perform(get("/api/price-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceHistory() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);

        int databaseSizeBeforeUpdate = priceHistoryRepository.findAll().size();

        // Update the priceHistory
        PriceHistory updatedPriceHistory = priceHistoryRepository.findById(priceHistory.getId()).get();
        // Disconnect from session so that the updates on updatedPriceHistory are not directly saved in db
        em.detach(updatedPriceHistory);
        updatedPriceHistory
            .price(UPDATED_PRICE);
        PriceHistoryDTO priceHistoryDTO = priceHistoryMapper.toDto(updatedPriceHistory);

        restPriceHistoryMockMvc.perform(put("/api/price-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(priceHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the PriceHistory in the database
        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeUpdate);
        PriceHistory testPriceHistory = priceHistoryList.get(priceHistoryList.size() - 1);
        assertThat(testPriceHistory.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceHistory() throws Exception {
        int databaseSizeBeforeUpdate = priceHistoryRepository.findAll().size();

        // Create the PriceHistory
        PriceHistoryDTO priceHistoryDTO = priceHistoryMapper.toDto(priceHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceHistoryMockMvc.perform(put("/api/price-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(priceHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceHistory in the database
        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePriceHistory() throws Exception {
        // Initialize the database
        priceHistoryRepository.saveAndFlush(priceHistory);

        int databaseSizeBeforeDelete = priceHistoryRepository.findAll().size();

        // Delete the priceHistory
        restPriceHistoryMockMvc.perform(delete("/api/price-histories/{id}", priceHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PriceHistory> priceHistoryList = priceHistoryRepository.findAll();
        assertThat(priceHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
