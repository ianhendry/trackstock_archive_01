package com.jugglerapps.stocktrack.service.impl;

import com.jugglerapps.stocktrack.service.PositionService;
import com.jugglerapps.stocktrack.domain.Position;
import com.jugglerapps.stocktrack.repository.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Position}.
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    private final Logger log = LoggerFactory.getLogger(PositionServiceImpl.class);

    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    /**
     * Save a position.
     *
     * @param position the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Position save(Position position) {
        log.debug("Request to save Position : {}", position);
        return positionRepository.save(position);
    }

    /**
     * Get all the positions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Position> findAll(Pageable pageable) {
        log.debug("Request to get all Positions");
        return positionRepository.findAll(pageable);
    }


    /**
     * Get one position by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Position> findOne(Long id) {
        log.debug("Request to get Position : {}", id);
        return positionRepository.findById(id);
    }

    /**
     * Delete the position by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Position : {}", id);
        positionRepository.deleteById(id);
    }
}
