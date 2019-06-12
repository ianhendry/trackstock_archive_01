package com.jugglerapps.stocktrack.service;

import com.jugglerapps.stocktrack.domain.Position;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Position}.
 */
public interface PositionService {

    /**
     * Save a position.
     *
     * @param position the entity to save.
     * @return the persisted entity.
     */
    Position save(Position position);

    /**
     * Get all the positions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Position> findAll(Pageable pageable);
    /**
     * Get all the PositionDTO where TradingAccount is {@code null}.
     *
     * @return the list of entities.
     */
    List<Position> findAllWhereTradingAccountIsNull();


    /**
     * Get the "id" position.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Position> findOne(Long id);

    /**
     * Delete the "id" position.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
