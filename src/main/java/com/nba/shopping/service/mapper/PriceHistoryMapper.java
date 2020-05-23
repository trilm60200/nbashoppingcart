package com.nba.shopping.service.mapper;


import com.nba.shopping.domain.*;
import com.nba.shopping.service.dto.PriceHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PriceHistory} and its DTO {@link PriceHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PriceHistoryMapper extends EntityMapper<PriceHistoryDTO, PriceHistory> {



    default PriceHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setId(id);
        return priceHistory;
    }
}
