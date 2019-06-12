package com.jugglerapps.stocktrack.service.impl;

import com.jugglerapps.stocktrack.service.WatchlistService;
import com.jugglerapps.stocktrack.domain.Watchlist;
import com.jugglerapps.stocktrack.repository.WatchlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Watchlist}.
 */
@Service
@Transactional
public class WatchlistServiceImpl implements WatchlistService {

    private final Logger log = LoggerFactory.getLogger(WatchlistServiceImpl.class);

    private final WatchlistRepository watchlistRepository;

    public WatchlistServiceImpl(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }

    /**
     * Save a watchlist.
     *
     * @param watchlist the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Watchlist save(Watchlist watchlist) {
        log.debug("Request to save Watchlist : {}", watchlist);
        return watchlistRepository.save(watchlist);
    }

    /**
     * Get all the watchlists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Watchlist> findAll(Pageable pageable) {
        log.debug("Request to get all Watchlists");
        return watchlistRepository.findAll(pageable);
    }

    /**
     * Get all the watchlists with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Watchlist> findAllWithEagerRelationships(Pageable pageable) {
        return watchlistRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one watchlist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Watchlist> findOne(Long id) {
        log.debug("Request to get Watchlist : {}", id);
        return watchlistRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the watchlist by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Watchlist : {}", id);
        watchlistRepository.deleteById(id);
    }
}
