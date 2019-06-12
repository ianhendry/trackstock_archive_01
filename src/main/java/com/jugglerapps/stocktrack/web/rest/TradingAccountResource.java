package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.domain.TradingAccount;
import com.jugglerapps.stocktrack.service.TradingAccountService;
import com.jugglerapps.stocktrack.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jugglerapps.stocktrack.domain.TradingAccount}.
 */
@RestController
@RequestMapping("/api")
public class TradingAccountResource {

    private final Logger log = LoggerFactory.getLogger(TradingAccountResource.class);

    private static final String ENTITY_NAME = "tradingAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TradingAccountService tradingAccountService;

    public TradingAccountResource(TradingAccountService tradingAccountService) {
        this.tradingAccountService = tradingAccountService;
    }

    /**
     * {@code POST  /trading-accounts} : Create a new tradingAccount.
     *
     * @param tradingAccount the tradingAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tradingAccount, or with status {@code 400 (Bad Request)} if the tradingAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trading-accounts")
    public ResponseEntity<TradingAccount> createTradingAccount(@Valid @RequestBody TradingAccount tradingAccount) throws URISyntaxException {
        log.debug("REST request to save TradingAccount : {}", tradingAccount);
        if (tradingAccount.getId() != null) {
            throw new BadRequestAlertException("A new tradingAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TradingAccount result = tradingAccountService.save(tradingAccount);
        return ResponseEntity.created(new URI("/api/trading-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trading-accounts} : Updates an existing tradingAccount.
     *
     * @param tradingAccount the tradingAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tradingAccount,
     * or with status {@code 400 (Bad Request)} if the tradingAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tradingAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trading-accounts")
    public ResponseEntity<TradingAccount> updateTradingAccount(@Valid @RequestBody TradingAccount tradingAccount) throws URISyntaxException {
        log.debug("REST request to update TradingAccount : {}", tradingAccount);
        if (tradingAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TradingAccount result = tradingAccountService.save(tradingAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tradingAccount.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trading-accounts} : get all the tradingAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tradingAccounts in body.
     */
    @GetMapping("/trading-accounts")
    public List<TradingAccount> getAllTradingAccounts() {
        log.debug("REST request to get all TradingAccounts");
        return tradingAccountService.findAll();
    }

    /**
     * {@code GET  /trading-accounts/:id} : get the "id" tradingAccount.
     *
     * @param id the id of the tradingAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tradingAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trading-accounts/{id}")
    public ResponseEntity<TradingAccount> getTradingAccount(@PathVariable Long id) {
        log.debug("REST request to get TradingAccount : {}", id);
        Optional<TradingAccount> tradingAccount = tradingAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tradingAccount);
    }

    /**
     * {@code DELETE  /trading-accounts/:id} : delete the "id" tradingAccount.
     *
     * @param id the id of the tradingAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trading-accounts/{id}")
    public ResponseEntity<Void> deleteTradingAccount(@PathVariable Long id) {
        log.debug("REST request to delete TradingAccount : {}", id);
        tradingAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
