package com.nba.shopping.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nba.shopping.web.rest.TestUtil;

public class PriceAuditDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceAuditDTO.class);
        PriceAuditDTO priceAuditDTO1 = new PriceAuditDTO();
        priceAuditDTO1.setId(1L);
        PriceAuditDTO priceAuditDTO2 = new PriceAuditDTO();
        assertThat(priceAuditDTO1).isNotEqualTo(priceAuditDTO2);
        priceAuditDTO2.setId(priceAuditDTO1.getId());
        assertThat(priceAuditDTO1).isEqualTo(priceAuditDTO2);
        priceAuditDTO2.setId(2L);
        assertThat(priceAuditDTO1).isNotEqualTo(priceAuditDTO2);
        priceAuditDTO1.setId(null);
        assertThat(priceAuditDTO1).isNotEqualTo(priceAuditDTO2);
    }
}
