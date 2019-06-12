package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.domain.Watchlist;
import com.jugglerapps.stocktrack.service.WatchlistService;
import com.jugglerapps.stocktrack.web.rest.errors.BadRequestAlertException;

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
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jugglerapps.stocktrack.domain.Watchlist}.
 */
@RestController
@RequestMapping("/api")
public class WatchlistResource {

    private final Logger log = LoggerFactory.getLogger(WatchlistResource.class);

    private static final String ENTITY_NAME = "watchlist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WatchlistService watchlistService;

    public WatchlistResource(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    /**
     * {@code POST  /watchlists} : Create a new watchlist.
     *
     * @param watchlist the watchlist to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new watchlist, or with status {@code 400 (Bad Request)} if the watchlist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/watchlists")
    public ResponseEntity<Watchlist> createWatchlist(@Valid @RequestBody Watchlist watchlist) throws URISyntaxException {
        log.debug("REST request to save Watchlist : {}", watchlist);
        if (watchlist.getId() != null) {
            throw new BadRequestAlertException("A new watchlist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Watchlist result = watchlistService.save(watchlist);
        return ResponseEntity.created(new URI("/api/watchlists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /watchlists} : Updates an existing watchlist.
     *
     * @param watchlist the watchlist to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchlist,
     * or with status {@code 400 (Bad Request)} if the watchlist is not valid,
     * or with status {@code 500 (Internal Server Error)} if the watchlist couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/watchlists")
    public ResponseEntity<Watchlist> updateWatchlist(@Valid @RequestBody Watchlist watchlist) throws URISyntaxException {
        log.debug("REST request to update Watchlist : {}", watchlist);
        if (watchlist.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Watchlist result = watchlistService.save(watchlist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, watchlist.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /watchlists} : get all the watchlists.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of watchlists in body.
     */
    @GetMapping("/watchlists")
    public ResponseEntity<List<Watchlist>> getAllWatchlists(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Watchlists");
        Page<Watchlist> page;
        if (eagerload) {
            page = watchlistService.findAllWithEagerRelationships(pageable);
        } else {
            page = watchlistService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /watchlists/:id} : get the "id" watchlist.
     *
     * @param id the id of the watchlist to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the watchlist, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/watchlists/{id}")
    public ResponseEntity<Watchlist> getWatchlist(@PathVariable Long id) {
        log.debug("REST request to get Watchlist : {}", id);
        Optional<Watchlist> watchlist = watchlistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(watchlist);
    }

    /**
     * {@code DELETE  /watchlists/:id} : delete the "id" watchlist.
     *
     * @param id the id of the watchlist to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/watchlists/{id}")
    public ResponseEntity<Void> deleteWatchlist(@PathVariable Long id) {
        log.debug("REST request to delete Watchlist : {}", id);
        watchlistService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
