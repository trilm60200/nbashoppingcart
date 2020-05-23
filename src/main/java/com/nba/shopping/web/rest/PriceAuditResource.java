package com.nba.shopping.web.rest;

import com.nba.shopping.service.PriceAuditService;
import com.nba.shopping.web.rest.errors.BadRequestAlertException;
import com.nba.shopping.service.dto.PriceAuditDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.nba.shopping.domain.PriceAudit}.
 */
@RestController
@RequestMapping("/api")
public class PriceAuditResource {

    private final Logger log = LoggerFactory.getLogger(PriceAuditResource.class);

    private final PriceAuditService priceAuditService;

    public PriceAuditResource(PriceAuditService priceAuditService) {
        this.priceAuditService = priceAuditService;
    }

    /**
     * {@code GET  /price-audits} : get all the priceAudits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priceAudits in body.
     */
    @GetMapping("/price-audits")
    public ResponseEntity<List<PriceAuditDTO>> getAllPriceAudits(Pageable pageable) {
        log.debug("REST request to get a page of PriceAudits");
        Page<PriceAuditDTO> page = priceAuditService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /price-audits/:id} : get the "id" priceAudit.
     *
     * @param id the id of the priceAuditDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the priceAuditDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/price-audits/{id}")
    public ResponseEntity<PriceAuditDTO> getPriceAudit(@PathVariable Long id) {
        log.debug("REST request to get PriceAudit : {}", id);
        Optional<PriceAuditDTO> priceAuditDTO = priceAuditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priceAuditDTO);
    }
}
