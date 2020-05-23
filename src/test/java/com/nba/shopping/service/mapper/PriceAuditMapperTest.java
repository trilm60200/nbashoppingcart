package com.nba.shopping.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PriceAuditMapperTest {

    private PriceAuditMapper priceAuditMapper;

    @BeforeEach
    public void setUp() {
        priceAuditMapper = new PriceAuditMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(priceAuditMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(priceAuditMapper.fromId(null)).isNull();
    }
}
