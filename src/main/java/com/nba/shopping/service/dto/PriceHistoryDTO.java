package com.nba.shopping.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nba.shopping.domain.PriceHistory} entity.
 */
public class PriceHistoryDTO implements Serializable {
    
    private Long id;

    private Integer price;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceHistoryDTO priceHistoryDTO = (PriceHistoryDTO) o;
        if (priceHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priceHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriceHistoryDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            "}";
    }
}
