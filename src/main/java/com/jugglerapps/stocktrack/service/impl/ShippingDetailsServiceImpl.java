package com.jugglerapps.stocktrack.service.impl;

import com.jugglerapps.stocktrack.service.ShippingDetailsService;
import com.jugglerapps.stocktrack.domain.ShippingDetails;
import com.jugglerapps.stocktrack.repository.ShippingDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ShippingDetails}.
 */
@Service
@Transactional
public class ShippingDetailsServiceImpl implements ShippingDetailsService {

    private final Logger log = LoggerFactory.getLogger(ShippingDetailsServiceImpl.class);

    private final ShippingDetailsRepository shippingDetailsRepository;

    public ShippingDetailsServiceImpl(ShippingDetailsRepository shippingDetailsRepository) {
        this.shippingDetailsRepository = shippingDetailsRepository;
    }

    /**
     * Save a shippingDetails.
     *
     * @param shippingDetails the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ShippingDetails save(ShippingDetails shippingDetails) {
        log.debug("Request to save ShippingDetails : {}", shippingDetails);
        return shippingDetailsRepository.save(shippingDetails);
    }

    /**
     * Get all the shippingDetails.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShippingDetails> findAll() {
        log.debug("Request to get all ShippingDetails");
        return shippingDetailsRepository.findAll();
    }


    /**
     * Get one shippingDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShippingDetails> findOne(Long id) {
        log.debug("Request to get ShippingDetails : {}", id);
        return shippingDetailsRepository.findById(id);
    }

    /**
     * Delete the shippingDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShippingDetails : {}", id);
        shippingDetailsRepository.deleteById(id);
    }
}
