package com.nba.shopping.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nba.shopping.web.rest.TestUtil;

public class PriceAuditTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceAudit.class);
        PriceAudit priceAudit1 = new PriceAudit();
        priceAudit1.setId(1L);
        PriceAudit priceAudit2 = new PriceAudit();
        priceAudit2.setId(priceAudit1.getId());
        assertThat(priceAudit1).isEqualTo(priceAudit2);
        priceAudit2.setId(2L);
        assertThat(priceAudit1).isNotEqualTo(priceAudit2);
        priceAudit1.setId(null);
        assertThat(priceAudit1).isNotEqualTo(priceAudit2);
    }
}
