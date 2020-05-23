package com.nba.shopping.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PriceHistoryMapperTest {

    private PriceHistoryMapper priceHistoryMapper;

    @BeforeEach
    public void setUp() {
        priceHistoryMapper = new PriceHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(priceHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(priceHistoryMapper.fromId(null)).isNull();
    }
}
