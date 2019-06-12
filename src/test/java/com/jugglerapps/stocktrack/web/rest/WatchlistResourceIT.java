package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.TrackstockApp;
import com.jugglerapps.stocktrack.domain.Watchlist;
import com.jugglerapps.stocktrack.repository.WatchlistRepository;
import com.jugglerapps.stocktrack.service.WatchlistService;
import com.jugglerapps.stocktrack.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.jugglerapps.stocktrack.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link WatchlistResource} REST controller.
 */
@SpringBootTest(classes = TrackstockApp.class)
public class WatchlistResourceIT {

    private static final String DEFAULT_WATCHLIST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WATCHLIST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WATCHLIST_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_WATCHLIST_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_INACTIVE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_INACTIVE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_WATCHLIST_INACTIVE = false;
    private static final Boolean UPDATED_WATCHLIST_INACTIVE = true;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Mock
    private WatchlistRepository watchlistRepositoryMock;

    @Mock
    private WatchlistService watchlistServiceMock;

    @Autowired
    private WatchlistService watchlistService;

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

    private MockMvc restWatchlistMockMvc;

    private Watchlist watchlist;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WatchlistResource watchlistResource = new WatchlistResource(watchlistService);
        this.restWatchlistMockMvc = MockMvcBuilders.standaloneSetup(watchlistResource)
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
    public static Watchlist createEntity(EntityManager em) {
        Watchlist watchlist = new Watchlist()
            .watchlistName(DEFAULT_WATCHLIST_NAME)
            .watchlistDescription(DEFAULT_WATCHLIST_DESCRIPTION)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateInactive(DEFAULT_DATE_INACTIVE)
            .watchlistInactive(DEFAULT_WATCHLIST_INACTIVE);
        return watchlist;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Watchlist createUpdatedEntity(EntityManager em) {
        Watchlist watchlist = new Watchlist()
            .watchlistName(UPDATED_WATCHLIST_NAME)
            .watchlistDescription(UPDATED_WATCHLIST_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateInactive(UPDATED_DATE_INACTIVE)
            .watchlistInactive(UPDATED_WATCHLIST_INACTIVE);
        return watchlist;
    }

    @BeforeEach
    public void initTest() {
        watchlist = createEntity(em);
    }

    @Test
    @Transactional
    public void createWatchlist() throws Exception {
        int databaseSizeBeforeCreate = watchlistRepository.findAll().size();

        // Create the Watchlist
        restWatchlistMockMvc.perform(post("/api/watchlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(watchlist)))
            .andExpect(status().isCreated());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeCreate + 1);
        Watchlist testWatchlist = watchlistList.get(watchlistList.size() - 1);
        assertThat(testWatchlist.getWatchlistName()).isEqualTo(DEFAULT_WATCHLIST_NAME);
        assertThat(testWatchlist.getWatchlistDescription()).isEqualTo(DEFAULT_WATCHLIST_DESCRIPTION);
        assertThat(testWatchlist.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testWatchlist.getDateInactive()).isEqualTo(DEFAULT_DATE_INACTIVE);
        assertThat(testWatchlist.isWatchlistInactive()).isEqualTo(DEFAULT_WATCHLIST_INACTIVE);
    }

    @Test
    @Transactional
    public void createWatchlistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = watchlistRepository.findAll().size();

        // Create the Watchlist with an existing ID
        watchlist.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchlistMockMvc.perform(post("/api/watchlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(watchlist)))
            .andExpect(status().isBadRequest());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWatchlistNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = watchlistRepository.findAll().size();
        // set the field null
        watchlist.setWatchlistName(null);

        // Create the Watchlist, which fails.

        restWatchlistMockMvc.perform(post("/api/watchlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(watchlist)))
            .andExpect(status().isBadRequest());

        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = watchlistRepository.findAll().size();
        // set the field null
        watchlist.setDateCreated(null);

        // Create the Watchlist, which fails.

        restWatchlistMockMvc.perform(post("/api/watchlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(watchlist)))
            .andExpect(status().isBadRequest());

        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWatchlists() throws Exception {
        // Initialize the database
        watchlistRepository.saveAndFlush(watchlist);

        // Get all the watchlistList
        restWatchlistMockMvc.perform(get("/api/watchlists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watchlist.getId().intValue())))
            .andExpect(jsonPath("$.[*].watchlistName").value(hasItem(DEFAULT_WATCHLIST_NAME.toString())))
            .andExpect(jsonPath("$.[*].watchlistDescription").value(hasItem(DEFAULT_WATCHLIST_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateInactive").value(hasItem(DEFAULT_DATE_INACTIVE.toString())))
            .andExpect(jsonPath("$.[*].watchlistInactive").value(hasItem(DEFAULT_WATCHLIST_INACTIVE.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllWatchlistsWithEagerRelationshipsIsEnabled() throws Exception {
        WatchlistResource watchlistResource = new WatchlistResource(watchlistServiceMock);
        when(watchlistServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restWatchlistMockMvc = MockMvcBuilders.standaloneSetup(watchlistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWatchlistMockMvc.perform(get("/api/watchlists?eagerload=true"))
        .andExpect(status().isOk());

        verify(watchlistServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllWatchlistsWithEagerRelationshipsIsNotEnabled() throws Exception {
        WatchlistResource watchlistResource = new WatchlistResource(watchlistServiceMock);
            when(watchlistServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restWatchlistMockMvc = MockMvcBuilders.standaloneSetup(watchlistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWatchlistMockMvc.perform(get("/api/watchlists?eagerload=true"))
        .andExpect(status().isOk());

            verify(watchlistServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getWatchlist() throws Exception {
        // Initialize the database
        watchlistRepository.saveAndFlush(watchlist);

        // Get the watchlist
        restWatchlistMockMvc.perform(get("/api/watchlists/{id}", watchlist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(watchlist.getId().intValue()))
            .andExpect(jsonPath("$.watchlistName").value(DEFAULT_WATCHLIST_NAME.toString()))
            .andExpect(jsonPath("$.watchlistDescription").value(DEFAULT_WATCHLIST_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateInactive").value(DEFAULT_DATE_INACTIVE.toString()))
            .andExpect(jsonPath("$.watchlistInactive").value(DEFAULT_WATCHLIST_INACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWatchlist() throws Exception {
        // Get the watchlist
        restWatchlistMockMvc.perform(get("/api/watchlists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWatchlist() throws Exception {
        // Initialize the database
        watchlistService.save(watchlist);

        int databaseSizeBeforeUpdate = watchlistRepository.findAll().size();

        // Update the watchlist
        Watchlist updatedWatchlist = watchlistRepository.findById(watchlist.getId()).get();
        // Disconnect from session so that the updates on updatedWatchlist are not directly saved in db
        em.detach(updatedWatchlist);
        updatedWatchlist
            .watchlistName(UPDATED_WATCHLIST_NAME)
            .watchlistDescription(UPDATED_WATCHLIST_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateInactive(UPDATED_DATE_INACTIVE)
            .watchlistInactive(UPDATED_WATCHLIST_INACTIVE);

        restWatchlistMockMvc.perform(put("/api/watchlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWatchlist)))
            .andExpect(status().isOk());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeUpdate);
        Watchlist testWatchlist = watchlistList.get(watchlistList.size() - 1);
        assertThat(testWatchlist.getWatchlistName()).isEqualTo(UPDATED_WATCHLIST_NAME);
        assertThat(testWatchlist.getWatchlistDescription()).isEqualTo(UPDATED_WATCHLIST_DESCRIPTION);
        assertThat(testWatchlist.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testWatchlist.getDateInactive()).isEqualTo(UPDATED_DATE_INACTIVE);
        assertThat(testWatchlist.isWatchlistInactive()).isEqualTo(UPDATED_WATCHLIST_INACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingWatchlist() throws Exception {
        int databaseSizeBeforeUpdate = watchlistRepository.findAll().size();

        // Create the Watchlist

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchlistMockMvc.perform(put("/api/watchlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(watchlist)))
            .andExpect(status().isBadRequest());

        // Validate the Watchlist in the database
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWatchlist() throws Exception {
        // Initialize the database
        watchlistService.save(watchlist);

        int databaseSizeBeforeDelete = watchlistRepository.findAll().size();

        // Delete the watchlist
        restWatchlistMockMvc.perform(delete("/api/watchlists/{id}", watchlist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Watchlist> watchlistList = watchlistRepository.findAll();
        assertThat(watchlistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Watchlist.class);
        Watchlist watchlist1 = new Watchlist();
        watchlist1.setId(1L);
        Watchlist watchlist2 = new Watchlist();
        watchlist2.setId(watchlist1.getId());
        assertThat(watchlist1).isEqualTo(watchlist2);
        watchlist2.setId(2L);
        assertThat(watchlist1).isNotEqualTo(watchlist2);
        watchlist1.setId(null);
        assertThat(watchlist1).isNotEqualTo(watchlist2);
    }
}
