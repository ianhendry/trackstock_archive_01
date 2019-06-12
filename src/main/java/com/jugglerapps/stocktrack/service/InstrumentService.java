package com.jugglerapps.stocktrack.service;

import com.jugglerapps.stocktrack.domain.Instrument;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Instrument}.
 */
public interface InstrumentService {

    /**
     * Save a instrument.
     *
     * @param instrument the entity to save.
     * @return the persisted entity.
     */
    Instrument save(Instrument instrument);

    /**
     * Get all the instruments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Instrument> findAll(Pageable pageable);
    /**
     * Get all the InstrumentDTO where Position is {@code null}.
     *
     * @return the list of entities.
     */
    List<Instrument> findAllWherePositionIsNull();
    /**
     * Get all the InstrumentDTO where Post is {@code null}.
     *
     * @return the list of entities.
     */
    List<Instrument> findAllWherePostIsNull();


    /**
     * Get the "id" instrument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Instrument> findOne(Long id);

    /**
     * Delete the "id" instrument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
