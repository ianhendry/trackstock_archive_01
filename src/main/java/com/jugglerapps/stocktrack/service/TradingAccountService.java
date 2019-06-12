package com.jugglerapps.stocktrack.service;

import com.jugglerapps.stocktrack.domain.TradingAccount;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TradingAccount}.
 */
public interface TradingAccountService {

    /**
     * Save a tradingAccount.
     *
     * @param tradingAccount the entity to save.
     * @return the persisted entity.
     */
    TradingAccount save(TradingAccount tradingAccount);

    /**
     * Get all the tradingAccounts.
     *
     * @return the list of entities.
     */
    List<TradingAccount> findAll();


    /**
     * Get the "id" tradingAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TradingAccount> findOne(Long id);

    /**
     * Delete the "id" tradingAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
