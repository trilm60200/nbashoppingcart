package com.nba.shopping.service.mapper;


import com.nba.shopping.domain.*;
import com.nba.shopping.service.dto.PriceAuditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PriceAudit} and its DTO {@link PriceAuditDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PriceAuditMapper extends EntityMapper<PriceAuditDTO, PriceAudit> {



    default PriceAudit fromId(Long id) {
        if (id == null) {
            return null;
        }
        PriceAudit priceAudit = new PriceAudit();
        priceAudit.setId(id);
        return priceAudit;
    }
}
