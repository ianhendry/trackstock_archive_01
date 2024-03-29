package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.domain.ShippingDetails;
import com.jugglerapps.stocktrack.service.ShippingDetailsService;
import com.jugglerapps.stocktrack.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jugglerapps.stocktrack.domain.ShippingDetails}.
 */
@RestController
@RequestMapping("/api")
public class ShippingDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ShippingDetailsResource.class);

    private static final String ENTITY_NAME = "shippingDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShippingDetailsService shippingDetailsService;

    public ShippingDetailsResource(ShippingDetailsService shippingDetailsService) {
        this.shippingDetailsService = shippingDetailsService;
    }

    /**
     * {@code POST  /shipping-details} : Create a new shippingDetails.
     *
     * @param shippingDetails the shippingDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shippingDetails, or with status {@code 400 (Bad Request)} if the shippingDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shipping-details")
    public ResponseEntity<ShippingDetails> createShippingDetails(@RequestBody ShippingDetails shippingDetails) throws URISyntaxException {
        log.debug("REST request to save ShippingDetails : {}", shippingDetails);
        if (shippingDetails.getId() != null) {
            throw new BadRequestAlertException("A new shippingDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShippingDetails result = shippingDetailsService.save(shippingDetails);
        return ResponseEntity.created(new URI("/api/shipping-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shipping-details} : Updates an existing shippingDetails.
     *
     * @param shippingDetails the shippingDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shippingDetails,
     * or with status {@code 400 (Bad Request)} if the shippingDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shippingDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shipping-details")
    public ResponseEntity<ShippingDetails> updateShippingDetails(@RequestBody ShippingDetails shippingDetails) throws URISyntaxException {
        log.debug("REST request to update ShippingDetails : {}", shippingDetails);
        if (shippingDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShippingDetails result = shippingDetailsService.save(shippingDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shippingDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shipping-details} : get all the shippingDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shippingDetails in body.
     */
    @GetMapping("/shipping-details")
    public List<ShippingDetails> getAllShippingDetails() {
        log.debug("REST request to get all ShippingDetails");
        return shippingDetailsService.findAll();
    }

    /**
     * {@code GET  /shipping-details/:id} : get the "id" shippingDetails.
     *
     * @param id the id of the shippingDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shippingDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shipping-details/{id}")
    public ResponseEntity<ShippingDetails> getShippingDetails(@PathVariable Long id) {
        log.debug("REST request to get ShippingDetails : {}", id);
        Optional<ShippingDetails> shippingDetails = shippingDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shippingDetails);
    }

    /**
     * {@code DELETE  /shipping-details/:id} : delete the "id" shippingDetails.
     *
     * @param id the id of the shippingDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shipping-details/{id}")
    public ResponseEntity<Void> deleteShippingDetails(@PathVariable Long id) {
        log.debug("REST request to delete ShippingDetails : {}", id);
        shippingDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
