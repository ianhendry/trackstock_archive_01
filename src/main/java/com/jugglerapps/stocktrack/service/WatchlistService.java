package com.jugglerapps.stocktrack.service;

import com.jugglerapps.stocktrack.domain.Watchlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Watchlist}.
 */
public interface WatchlistService {

    /**
     * Save a watchlist.
     *
     * @param watchlist the entity to save.
     * @return the persisted entity.
     */
    Watchlist save(Watchlist watchlist);

    /**
     * Get all the watchlists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Watchlist> findAll(Pageable pageable);

    /**
     * Get all the watchlists with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Watchlist> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" watchlist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Watchlist> findOne(Long id);

    /**
     * Delete the "id" watchlist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
