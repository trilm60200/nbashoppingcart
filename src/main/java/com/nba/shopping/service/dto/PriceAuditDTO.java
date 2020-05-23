package com.nba.shopping.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nba.shopping.domain.PriceAudit} entity.
 */
public class PriceAuditDTO implements Serializable {
    
    private Long id;

    private Long productId;

    private String productName;

    private Integer price;

    private LocalDate createDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceAuditDTO priceAuditDTO = (PriceAuditDTO) o;
        if (priceAuditDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priceAuditDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriceAuditDTO{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            ", price=" + getPrice() +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }
}
