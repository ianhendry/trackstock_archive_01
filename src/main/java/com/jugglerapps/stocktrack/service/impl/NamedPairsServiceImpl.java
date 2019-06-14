package com.jugglerapps.stocktrack.service.impl;

import com.jugglerapps.stocktrack.service.NamedPairsService;
import com.jugglerapps.stocktrack.domain.NamedPairs;
import com.jugglerapps.stocktrack.repository.NamedPairsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link NamedPairs}.
 */
@Service
@Transactional
public class NamedPairsServiceImpl implements NamedPairsService {

    private final Logger log = LoggerFactory.getLogger(NamedPairsServiceImpl.class);

    private final NamedPairsRepository namedPairsRepository;

    public NamedPairsServiceImpl(NamedPairsRepository namedPairsRepository) {
        this.namedPairsRepository = namedPairsRepository;
    }

    /**
     * Save a namedPairs.
     *
     * @param namedPairs the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NamedPairs save(NamedPairs namedPairs) {
        log.debug("Request to save NamedPairs : {}", namedPairs);
        return namedPairsRepository.save(namedPairs);
    }

    /**
     * Get all the namedPairs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<NamedPairs> findAll() {
        log.debug("Request to get all NamedPairs");
        return namedPairsRepository.findAll();
    }


    /**
     * Get one namedPairs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NamedPairs> findOne(Long id) {
        log.debug("Request to get NamedPairs : {}", id);
        return namedPairsRepository.findById(id);
    }

    /**
     * Delete the namedPairs by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NamedPairs : {}", id);
        namedPairsRepository.deleteById(id);
    }
}
