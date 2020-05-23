package com.nba.shopping.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.nba.shopping.web.rest.TestUtil;

public class PriceHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceHistory.class);
        PriceHistory priceHistory1 = new PriceHistory();
        priceHistory1.setId(1L);
        PriceHistory priceHistory2 = new PriceHistory();
        priceHistory2.setId(priceHistory1.getId());
        assertThat(priceHistory1).isEqualTo(priceHistory2);
        priceHistory2.setId(2L);
        assertThat(priceHistory1).isNotEqualTo(priceHistory2);
        priceHistory1.setId(null);
        assertThat(priceHistory1).isNotEqualTo(priceHistory2);
    }
}
