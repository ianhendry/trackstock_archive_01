package com.jugglerapps.stocktrack.service;

import com.jugglerapps.stocktrack.domain.NamedPairs;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link NamedPairs}.
 */
public interface NamedPairsService {

    /**
     * Save a namedPairs.
     *
     * @param namedPairs the entity to save.
     * @return the persisted entity.
     */
    NamedPairs save(NamedPairs namedPairs);

    /**
     * Get all the namedPairs.
     *
     * @return the list of entities.
     */
    List<NamedPairs> findAll();


    /**
     * Get the "id" namedPairs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NamedPairs> findOne(Long id);

    /**
     * Delete the "id" namedPairs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
