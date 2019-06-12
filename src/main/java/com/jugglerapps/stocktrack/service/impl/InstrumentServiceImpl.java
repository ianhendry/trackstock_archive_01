package com.jugglerapps.stocktrack.service.impl;

import com.jugglerapps.stocktrack.service.InstrumentService;
import com.jugglerapps.stocktrack.domain.Instrument;
import com.jugglerapps.stocktrack.repository.InstrumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Instrument}.
 */
@Service
@Transactional
public class InstrumentServiceImpl implements InstrumentService {

    private final Logger log = LoggerFactory.getLogger(InstrumentServiceImpl.class);

    private final InstrumentRepository instrumentRepository;

    public InstrumentServiceImpl(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }

    /**
     * Save a instrument.
     *
     * @param instrument the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Instrument save(Instrument instrument) {
        log.debug("Request to save Instrument : {}", instrument);
        return instrumentRepository.save(instrument);
    }

    /**
     * Get all the instruments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Instrument> findAll(Pageable pageable) {
        log.debug("Request to get all Instruments");
        return instrumentRepository.findAll(pageable);
    }



    /**
    *  Get all the instruments where Position is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Instrument> findAllWherePositionIsNull() {
        log.debug("Request to get all instruments where Position is null");
        return StreamSupport
            .stream(instrumentRepository.findAll().spliterator(), false)
            .filter(instrument -> instrument.getPosition() == null)
            .collect(Collectors.toList());
    }


    /**
    *  Get all the instruments where Post is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Instrument> findAllWherePostIsNull() {
        log.debug("Request to get all instruments where Post is null");
        return StreamSupport
            .stream(instrumentRepository.findAll().spliterator(), false)
            .filter(instrument -> instrument.getPost() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one instrument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Instrument> findOne(Long id) {
        log.debug("Request to get Instrument : {}", id);
        return instrumentRepository.findById(id);
    }

    /**
     * Delete the instrument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Instrument : {}", id);
        instrumentRepository.deleteById(id);
    }
}
