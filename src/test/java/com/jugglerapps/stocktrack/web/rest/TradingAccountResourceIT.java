package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.TrackstockApp;
import com.jugglerapps.stocktrack.domain.TradingAccount;
import com.jugglerapps.stocktrack.repository.TradingAccountRepository;
import com.jugglerapps.stocktrack.service.TradingAccountService;
import com.jugglerapps.stocktrack.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.jugglerapps.stocktrack.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TradingAccountResource} REST controller.
 */
@SpringBootTest(classes = TrackstockApp.class)
public class TradingAccountResourceIT {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACCOUNT_REAL = false;
    private static final Boolean UPDATED_ACCOUNT_REAL = true;

    private static final LocalDate DEFAULT_ACCOUNT_OPEN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCOUNT_OPEN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_ACCOUNT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACCOUNT_BALANCE = new BigDecimal(2);

    private static final LocalDate DEFAULT_ACCOUNT_CLOSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCOUNT_CLOSE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TradingAccountRepository tradingAccountRepository;

    @Autowired
    private TradingAccountService tradingAccountService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTradingAccountMockMvc;

    private TradingAccount tradingAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TradingAccountResource tradingAccountResource = new TradingAccountResource(tradingAccountService);
        this.restTradingAccountMockMvc = MockMvcBuilders.standaloneSetup(tradingAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradingAccount createEntity(EntityManager em) {
        TradingAccount tradingAccount = new TradingAccount()
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountReal(DEFAULT_ACCOUNT_REAL)
            .accountOpenDate(DEFAULT_ACCOUNT_OPEN_DATE)
            .accountBalance(DEFAULT_ACCOUNT_BALANCE)
            .accountCloseDate(DEFAULT_ACCOUNT_CLOSE_DATE);
        return tradingAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradingAccount createUpdatedEntity(EntityManager em) {
        TradingAccount tradingAccount = new TradingAccount()
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountReal(UPDATED_ACCOUNT_REAL)
            .accountOpenDate(UPDATED_ACCOUNT_OPEN_DATE)
            .accountBalance(UPDATED_ACCOUNT_BALANCE)
            .accountCloseDate(UPDATED_ACCOUNT_CLOSE_DATE);
        return tradingAccount;
    }

    @BeforeEach
    public void initTest() {
        tradingAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createTradingAccount() throws Exception {
        int databaseSizeBeforeCreate = tradingAccountRepository.findAll().size();

        // Create the TradingAccount
        restTradingAccountMockMvc.perform(post("/api/trading-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingAccount)))
            .andExpect(status().isCreated());

        // Validate the TradingAccount in the database
        List<TradingAccount> tradingAccountList = tradingAccountRepository.findAll();
        assertThat(tradingAccountList).hasSize(databaseSizeBeforeCreate + 1);
        TradingAccount testTradingAccount = tradingAccountList.get(tradingAccountList.size() - 1);
        assertThat(testTradingAccount.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testTradingAccount.isAccountReal()).isEqualTo(DEFAULT_ACCOUNT_REAL);
        assertThat(testTradingAccount.getAccountOpenDate()).isEqualTo(DEFAULT_ACCOUNT_OPEN_DATE);
        assertThat(testTradingAccount.getAccountBalance()).isEqualTo(DEFAULT_ACCOUNT_BALANCE);
        assertThat(testTradingAccount.getAccountCloseDate()).isEqualTo(DEFAULT_ACCOUNT_CLOSE_DATE);
    }

    @Test
    @Transactional
    public void createTradingAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tradingAccountRepository.findAll().size();

        // Create the TradingAccount with an existing ID
        tradingAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTradingAccountMockMvc.perform(post("/api/trading-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingAccount)))
            .andExpect(status().isBadRequest());

        // Validate the TradingAccount in the database
        List<TradingAccount> tradingAccountList = tradingAccountRepository.findAll();
        assertThat(tradingAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingAccountRepository.findAll().size();
        // set the field null
        tradingAccount.setAccountName(null);

        // Create the TradingAccount, which fails.

        restTradingAccountMockMvc.perform(post("/api/trading-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingAccount)))
            .andExpect(status().isBadRequest());

        List<TradingAccount> tradingAccountList = tradingAccountRepository.findAll();
        assertThat(tradingAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountRealIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingAccountRepository.findAll().size();
        // set the field null
        tradingAccount.setAccountReal(null);

        // Create the TradingAccount, which fails.

        restTradingAccountMockMvc.perform(post("/api/trading-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingAccount)))
            .andExpect(status().isBadRequest());

        List<TradingAccount> tradingAccountList = tradingAccountRepository.findAll();
        assertThat(tradingAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountOpenDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingAccountRepository.findAll().size();
        // set the field null
        tradingAccount.setAccountOpenDate(null);

        // Create the TradingAccount, which fails.

        restTradingAccountMockMvc.perform(post("/api/trading-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingAccount)))
            .andExpect(status().isBadRequest());

        List<TradingAccount> tradingAccountList = tradingAccountRepository.findAll();
        assertThat(tradingAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingAccountRepository.findAll().size();
        // set the field null
        tradingAccount.setAccountBalance(null);

        // Create the TradingAccount, which fails.

        restTradingAccountMockMvc.perform(post("/api/trading-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingAccount)))
            .andExpect(status().isBadRequest());

        List<TradingAccount> tradingAccountList = tradingAccountRepository.findAll();
        assertThat(tradingAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTradingAccounts() throws Exception {
        // Initialize the database
        tradingAccountRepository.saveAndFlush(tradingAccount);

        // Get all the tradingAccountList
        restTradingAccountMockMvc.perform(get("/api/trading-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tradingAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountReal").value(hasItem(DEFAULT_ACCOUNT_REAL.booleanValue())))
            .andExpect(jsonPath("$.[*].accountOpenDate").value(hasItem(DEFAULT_ACCOUNT_OPEN_DATE.toString())))
            .andExpect(jsonPath("$.[*].accountBalance").value(hasItem(DEFAULT_ACCOUNT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].accountCloseDate").value(hasItem(DEFAULT_ACCOUNT_CLOSE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTradingAccount() throws Exception {
        // Initialize the database
        tradingAccountRepository.saveAndFlush(tradingAccount);

        // Get the tradingAccount
        restTradingAccountMockMvc.perform(get("/api/trading-accounts/{id}", tradingAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tradingAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.accountReal").value(DEFAULT_ACCOUNT_REAL.booleanValue()))
            .andExpect(jsonPath("$.accountOpenDate").value(DEFAULT_ACCOUNT_OPEN_DATE.toString()))
            .andExpect(jsonPath("$.accountBalance").value(DEFAULT_ACCOUNT_BALANCE.intValue()))
            .andExpect(jsonPath("$.accountCloseDate").value(DEFAULT_ACCOUNT_CLOSE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTradingAccount() throws Exception {
        // Get the tradingAccount
        restTradingAccountMockMvc.perform(get("/api/trading-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTradingAccount() throws Exception {
        // Initialize the database
        tradingAccountService.save(tradingAccount);

        int databaseSizeBeforeUpdate = tradingAccountRepository.findAll().size();

        // Update the tradingAccount
        TradingAccount updatedTradingAccount = tradingAccountRepository.findById(tradingAccount.getId()).get();
        // Disconnect from session so that the updates on updatedTradingAccount are not directly saved in db
        em.detach(updatedTradingAccount);
        updatedTradingAccount
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountReal(UPDATED_ACCOUNT_REAL)
            .accountOpenDate(UPDATED_ACCOUNT_OPEN_DATE)
            .accountBalance(UPDATED_ACCOUNT_BALANCE)
            .accountCloseDate(UPDATED_ACCOUNT_CLOSE_DATE);

        restTradingAccountMockMvc.perform(put("/api/trading-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTradingAccount)))
            .andExpect(status().isOk());

        // Validate the TradingAccount in the database
        List<TradingAccount> tradingAccountList = tradingAccountRepository.findAll();
        assertThat(tradingAccountList).hasSize(databaseSizeBeforeUpdate);
        TradingAccount testTradingAccount = tradingAccountList.get(tradingAccountList.size() - 1);
        assertThat(testTradingAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testTradingAccount.isAccountReal()).isEqualTo(UPDATED_ACCOUNT_REAL);
        assertThat(testTradingAccount.getAccountOpenDate()).isEqualTo(UPDATED_ACCOUNT_OPEN_DATE);
        assertThat(testTradingAccount.getAccountBalance()).isEqualTo(UPDATED_ACCOUNT_BALANCE);
        assertThat(testTradingAccount.getAccountCloseDate()).isEqualTo(UPDATED_ACCOUNT_CLOSE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTradingAccount() throws Exception {
        int databaseSizeBeforeUpdate = tradingAccountRepository.findAll().size();

        // Create the TradingAccount

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradingAccountMockMvc.perform(put("/api/trading-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tradingAccount)))
            .andExpect(status().isBadRequest());

        // Validate the TradingAccount in the database
        List<TradingAccount> tradingAccountList = tradingAccountRepository.findAll();
        assertThat(tradingAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTradingAccount() throws Exception {
        // Initialize the database
        tradingAccountService.save(tradingAccount);

        int databaseSizeBeforeDelete = tradingAccountRepository.findAll().size();

        // Delete the tradingAccount
        restTradingAccountMockMvc.perform(delete("/api/trading-accounts/{id}", tradingAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TradingAccount> tradingAccountList = tradingAccountRepository.findAll();
        assertThat(tradingAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TradingAccount.class);
        TradingAccount tradingAccount1 = new TradingAccount();
        tradingAccount1.setId(1L);
        TradingAccount tradingAccount2 = new TradingAccount();
        tradingAccount2.setId(tradingAccount1.getId());
        assertThat(tradingAccount1).isEqualTo(tradingAccount2);
        tradingAccount2.setId(2L);
        assertThat(tradingAccount1).isNotEqualTo(tradingAccount2);
        tradingAccount1.setId(null);
        assertThat(tradingAccount1).isNotEqualTo(tradingAccount2);
    }
}
