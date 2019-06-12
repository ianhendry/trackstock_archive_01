package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.TrackstockApp;
import com.jugglerapps.stocktrack.domain.Instrument;
import com.jugglerapps.stocktrack.repository.InstrumentRepository;
import com.jugglerapps.stocktrack.service.InstrumentService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.jugglerapps.stocktrack.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jugglerapps.stocktrack.domain.enumeration.FinancialDataSources;
/**
 * Integration tests for the {@Link InstrumentResource} REST controller.
 */
@SpringBootTest(classes = TrackstockApp.class)
public class InstrumentResourceIT {

    private static final FinancialDataSources DEFAULT_DATA_PROVIDER = FinancialDataSources.FXPRO;
    private static final FinancialDataSources UPDATED_DATA_PROVIDER = FinancialDataSources.QUANDLL;

    private static final String DEFAULT_INSTRUMENT_TICKER = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENT_TICKER = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUMENT_EXCHNAGE = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENT_EXCHNAGE = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUMENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUMENT_DATA_FROM = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENT_DATA_FROM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INSTRUMENT_ACTIVE = false;
    private static final Boolean UPDATED_INSTRUMENT_ACTIVE = true;

    private static final LocalDate DEFAULT_DATE_ADDED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ADDED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_INACTIVE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_INACTIVE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private InstrumentService instrumentService;

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

    private MockMvc restInstrumentMockMvc;

    private Instrument instrument;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstrumentResource instrumentResource = new InstrumentResource(instrumentService);
        this.restInstrumentMockMvc = MockMvcBuilders.standaloneSetup(instrumentResource)
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
    public static Instrument createEntity(EntityManager em) {
        Instrument instrument = new Instrument()
            .dataProvider(DEFAULT_DATA_PROVIDER)
            .instrumentTicker(DEFAULT_INSTRUMENT_TICKER)
            .instrumentExchnage(DEFAULT_INSTRUMENT_EXCHNAGE)
            .instrumentDescription(DEFAULT_INSTRUMENT_DESCRIPTION)
            .instrumentDataFrom(DEFAULT_INSTRUMENT_DATA_FROM)
            .instrumentActive(DEFAULT_INSTRUMENT_ACTIVE)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateInactive(DEFAULT_DATE_INACTIVE);
        return instrument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instrument createUpdatedEntity(EntityManager em) {
        Instrument instrument = new Instrument()
            .dataProvider(UPDATED_DATA_PROVIDER)
            .instrumentTicker(UPDATED_INSTRUMENT_TICKER)
            .instrumentExchnage(UPDATED_INSTRUMENT_EXCHNAGE)
            .instrumentDescription(UPDATED_INSTRUMENT_DESCRIPTION)
            .instrumentDataFrom(UPDATED_INSTRUMENT_DATA_FROM)
            .instrumentActive(UPDATED_INSTRUMENT_ACTIVE)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateInactive(UPDATED_DATE_INACTIVE);
        return instrument;
    }

    @BeforeEach
    public void initTest() {
        instrument = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstrument() throws Exception {
        int databaseSizeBeforeCreate = instrumentRepository.findAll().size();

        // Create the Instrument
        restInstrumentMockMvc.perform(post("/api/instruments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrument)))
            .andExpect(status().isCreated());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeCreate + 1);
        Instrument testInstrument = instrumentList.get(instrumentList.size() - 1);
        assertThat(testInstrument.getDataProvider()).isEqualTo(DEFAULT_DATA_PROVIDER);
        assertThat(testInstrument.getInstrumentTicker()).isEqualTo(DEFAULT_INSTRUMENT_TICKER);
        assertThat(testInstrument.getInstrumentExchnage()).isEqualTo(DEFAULT_INSTRUMENT_EXCHNAGE);
        assertThat(testInstrument.getInstrumentDescription()).isEqualTo(DEFAULT_INSTRUMENT_DESCRIPTION);
        assertThat(testInstrument.getInstrumentDataFrom()).isEqualTo(DEFAULT_INSTRUMENT_DATA_FROM);
        assertThat(testInstrument.isInstrumentActive()).isEqualTo(DEFAULT_INSTRUMENT_ACTIVE);
        assertThat(testInstrument.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testInstrument.getDateInactive()).isEqualTo(DEFAULT_DATE_INACTIVE);
    }

    @Test
    @Transactional
    public void createInstrumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instrumentRepository.findAll().size();

        // Create the Instrument with an existing ID
        instrument.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstrumentMockMvc.perform(post("/api/instruments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrument)))
            .andExpect(status().isBadRequest());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInstrumentTickerIsRequired() throws Exception {
        int databaseSizeBeforeTest = instrumentRepository.findAll().size();
        // set the field null
        instrument.setInstrumentTicker(null);

        // Create the Instrument, which fails.

        restInstrumentMockMvc.perform(post("/api/instruments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrument)))
            .andExpect(status().isBadRequest());

        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstruments() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Get all the instrumentList
        restInstrumentMockMvc.perform(get("/api/instruments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instrument.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataProvider").value(hasItem(DEFAULT_DATA_PROVIDER.toString())))
            .andExpect(jsonPath("$.[*].instrumentTicker").value(hasItem(DEFAULT_INSTRUMENT_TICKER.toString())))
            .andExpect(jsonPath("$.[*].instrumentExchnage").value(hasItem(DEFAULT_INSTRUMENT_EXCHNAGE.toString())))
            .andExpect(jsonPath("$.[*].instrumentDescription").value(hasItem(DEFAULT_INSTRUMENT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].instrumentDataFrom").value(hasItem(DEFAULT_INSTRUMENT_DATA_FROM.toString())))
            .andExpect(jsonPath("$.[*].instrumentActive").value(hasItem(DEFAULT_INSTRUMENT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].dateInactive").value(hasItem(DEFAULT_DATE_INACTIVE.toString())));
    }
    
    @Test
    @Transactional
    public void getInstrument() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Get the instrument
        restInstrumentMockMvc.perform(get("/api/instruments/{id}", instrument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(instrument.getId().intValue()))
            .andExpect(jsonPath("$.dataProvider").value(DEFAULT_DATA_PROVIDER.toString()))
            .andExpect(jsonPath("$.instrumentTicker").value(DEFAULT_INSTRUMENT_TICKER.toString()))
            .andExpect(jsonPath("$.instrumentExchnage").value(DEFAULT_INSTRUMENT_EXCHNAGE.toString()))
            .andExpect(jsonPath("$.instrumentDescription").value(DEFAULT_INSTRUMENT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.instrumentDataFrom").value(DEFAULT_INSTRUMENT_DATA_FROM.toString()))
            .andExpect(jsonPath("$.instrumentActive").value(DEFAULT_INSTRUMENT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()))
            .andExpect(jsonPath("$.dateInactive").value(DEFAULT_DATE_INACTIVE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstrument() throws Exception {
        // Get the instrument
        restInstrumentMockMvc.perform(get("/api/instruments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstrument() throws Exception {
        // Initialize the database
        instrumentService.save(instrument);

        int databaseSizeBeforeUpdate = instrumentRepository.findAll().size();

        // Update the instrument
        Instrument updatedInstrument = instrumentRepository.findById(instrument.getId()).get();
        // Disconnect from session so that the updates on updatedInstrument are not directly saved in db
        em.detach(updatedInstrument);
        updatedInstrument
            .dataProvider(UPDATED_DATA_PROVIDER)
            .instrumentTicker(UPDATED_INSTRUMENT_TICKER)
            .instrumentExchnage(UPDATED_INSTRUMENT_EXCHNAGE)
            .instrumentDescription(UPDATED_INSTRUMENT_DESCRIPTION)
            .instrumentDataFrom(UPDATED_INSTRUMENT_DATA_FROM)
            .instrumentActive(UPDATED_INSTRUMENT_ACTIVE)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateInactive(UPDATED_DATE_INACTIVE);

        restInstrumentMockMvc.perform(put("/api/instruments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstrument)))
            .andExpect(status().isOk());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeUpdate);
        Instrument testInstrument = instrumentList.get(instrumentList.size() - 1);
        assertThat(testInstrument.getDataProvider()).isEqualTo(UPDATED_DATA_PROVIDER);
        assertThat(testInstrument.getInstrumentTicker()).isEqualTo(UPDATED_INSTRUMENT_TICKER);
        assertThat(testInstrument.getInstrumentExchnage()).isEqualTo(UPDATED_INSTRUMENT_EXCHNAGE);
        assertThat(testInstrument.getInstrumentDescription()).isEqualTo(UPDATED_INSTRUMENT_DESCRIPTION);
        assertThat(testInstrument.getInstrumentDataFrom()).isEqualTo(UPDATED_INSTRUMENT_DATA_FROM);
        assertThat(testInstrument.isInstrumentActive()).isEqualTo(UPDATED_INSTRUMENT_ACTIVE);
        assertThat(testInstrument.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testInstrument.getDateInactive()).isEqualTo(UPDATED_DATE_INACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingInstrument() throws Exception {
        int databaseSizeBeforeUpdate = instrumentRepository.findAll().size();

        // Create the Instrument

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstrumentMockMvc.perform(put("/api/instruments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrument)))
            .andExpect(status().isBadRequest());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstrument() throws Exception {
        // Initialize the database
        instrumentService.save(instrument);

        int databaseSizeBeforeDelete = instrumentRepository.findAll().size();

        // Delete the instrument
        restInstrumentMockMvc.perform(delete("/api/instruments/{id}", instrument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Instrument.class);
        Instrument instrument1 = new Instrument();
        instrument1.setId(1L);
        Instrument instrument2 = new Instrument();
        instrument2.setId(instrument1.getId());
        assertThat(instrument1).isEqualTo(instrument2);
        instrument2.setId(2L);
        assertThat(instrument1).isNotEqualTo(instrument2);
        instrument1.setId(null);
        assertThat(instrument1).isNotEqualTo(instrument2);
    }
}
