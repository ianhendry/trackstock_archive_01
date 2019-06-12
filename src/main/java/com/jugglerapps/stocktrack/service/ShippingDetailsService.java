package com.jugglerapps.stocktrack.service;

import com.jugglerapps.stocktrack.domain.ShippingDetails;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ShippingDetails}.
 */
public interface ShippingDetailsService {

    /**
     * Save a shippingDetails.
     *
     * @param shippingDetails the entity to save.
     * @return the persisted entity.
     */
    ShippingDetails save(ShippingDetails shippingDetails);

    /**
     * Get all the shippingDetails.
     *
     * @return the list of entities.
     */
    List<ShippingDetails> findAll();


    /**
     * Get the "id" shippingDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShippingDetails> findOne(Long id);

    /**
     * Delete the "id" shippingDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
