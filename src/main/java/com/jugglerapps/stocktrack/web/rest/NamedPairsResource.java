package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.domain.NamedPairs;
import com.jugglerapps.stocktrack.service.NamedPairsService;
import com.jugglerapps.stocktrack.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jugglerapps.stocktrack.domain.NamedPairs}.
 */
@RestController
@RequestMapping("/api")
public class NamedPairsResource {

    private final Logger log = LoggerFactory.getLogger(NamedPairsResource.class);

    private static final String ENTITY_NAME = "namedPairs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NamedPairsService namedPairsService;

    public NamedPairsResource(NamedPairsService namedPairsService) {
        this.namedPairsService = namedPairsService;
    }

    /**
     * {@code POST  /named-pairs} : Create a new namedPairs.
     *
     * @param namedPairs the namedPairs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new namedPairs, or with status {@code 400 (Bad Request)} if the namedPairs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/named-pairs")
    public ResponseEntity<NamedPairs> createNamedPairs(@Valid @RequestBody NamedPairs namedPairs) throws URISyntaxException {
        log.debug("REST request to save NamedPairs : {}", namedPairs);
        if (namedPairs.getId() != null) {
            throw new BadRequestAlertException("A new namedPairs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NamedPairs result = namedPairsService.save(namedPairs);
        return ResponseEntity.created(new URI("/api/named-pairs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /named-pairs} : Updates an existing namedPairs.
     *
     * @param namedPairs the namedPairs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated namedPairs,
     * or with status {@code 400 (Bad Request)} if the namedPairs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the namedPairs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/named-pairs")
    public ResponseEntity<NamedPairs> updateNamedPairs(@Valid @RequestBody NamedPairs namedPairs) throws URISyntaxException {
        log.debug("REST request to update NamedPairs : {}", namedPairs);
        if (namedPairs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NamedPairs result = namedPairsService.save(namedPairs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, namedPairs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /named-pairs} : get all the namedPairs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of namedPairs in body.
     */
    @GetMapping("/named-pairs")
    public List<NamedPairs> getAllNamedPairs() {
        log.debug("REST request to get all NamedPairs");
        return namedPairsService.findAll();
    }

    /**
     * {@code GET  /named-pairs/:id} : get the "id" namedPairs.
     *
     * @param id the id of the namedPairs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the namedPairs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/named-pairs/{id}")
    public ResponseEntity<NamedPairs> getNamedPairs(@PathVariable Long id) {
        log.debug("REST request to get NamedPairs : {}", id);
        Optional<NamedPairs> namedPairs = namedPairsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(namedPairs);
    }

    /**
     * {@code DELETE  /named-pairs/:id} : delete the "id" namedPairs.
     *
     * @param id the id of the namedPairs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/named-pairs/{id}")
    public ResponseEntity<Void> deleteNamedPairs(@PathVariable Long id) {
        log.debug("REST request to delete NamedPairs : {}", id);
        namedPairsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
