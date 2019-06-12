package com.jugglerapps.stocktrack.service.impl;

import com.jugglerapps.stocktrack.service.TradingAccountService;
import com.jugglerapps.stocktrack.domain.TradingAccount;
import com.jugglerapps.stocktrack.repository.TradingAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TradingAccount}.
 */
@Service
@Transactional
public class TradingAccountServiceImpl implements TradingAccountService {

    private final Logger log = LoggerFactory.getLogger(TradingAccountServiceImpl.class);

    private final TradingAccountRepository tradingAccountRepository;

    public TradingAccountServiceImpl(TradingAccountRepository tradingAccountRepository) {
        this.tradingAccountRepository = tradingAccountRepository;
    }

    /**
     * Save a tradingAccount.
     *
     * @param tradingAccount the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TradingAccount save(TradingAccount tradingAccount) {
        log.debug("Request to save TradingAccount : {}", tradingAccount);
        return tradingAccountRepository.save(tradingAccount);
    }

    /**
     * Get all the tradingAccounts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TradingAccount> findAll() {
        log.debug("Request to get all TradingAccounts");
        return tradingAccountRepository.findAll();
    }


    /**
     * Get one tradingAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TradingAccount> findOne(Long id) {
        log.debug("Request to get TradingAccount : {}", id);
        return tradingAccountRepository.findById(id);
    }

    /**
     * Delete the tradingAccount by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TradingAccount : {}", id);
        tradingAccountRepository.deleteById(id);
    }
}
