package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.TrackstockApp;
import com.jugglerapps.stocktrack.domain.NamedPairs;
import com.jugglerapps.stocktrack.repository.NamedPairsRepository;
import com.jugglerapps.stocktrack.service.NamedPairsService;
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
import java.util.List;

import static com.jugglerapps.stocktrack.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link NamedPairsResource} REST controller.
 */
@SpringBootTest(classes = TrackstockApp.class)
public class NamedPairsResourceIT {

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private NamedPairsRepository namedPairsRepository;

    @Autowired
    private NamedPairsService namedPairsService;

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

    private MockMvc restNamedPairsMockMvc;

    private NamedPairs namedPairs;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NamedPairsResource namedPairsResource = new NamedPairsResource(namedPairsService);
        this.restNamedPairsMockMvc = MockMvcBuilders.standaloneSetup(namedPairsResource)
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
    public static NamedPairs createEntity(EntityManager em) {
        NamedPairs namedPairs = new NamedPairs()
            .groupName(DEFAULT_GROUP_NAME)
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return namedPairs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NamedPairs createUpdatedEntity(EntityManager em) {
        NamedPairs namedPairs = new NamedPairs()
            .groupName(UPDATED_GROUP_NAME)
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        return namedPairs;
    }

    @BeforeEach
    public void initTest() {
        namedPairs = createEntity(em);
    }

    @Test
    @Transactional
    public void createNamedPairs() throws Exception {
        int databaseSizeBeforeCreate = namedPairsRepository.findAll().size();

        // Create the NamedPairs
        restNamedPairsMockMvc.perform(post("/api/named-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(namedPairs)))
            .andExpect(status().isCreated());

        // Validate the NamedPairs in the database
        List<NamedPairs> namedPairsList = namedPairsRepository.findAll();
        assertThat(namedPairsList).hasSize(databaseSizeBeforeCreate + 1);
        NamedPairs testNamedPairs = namedPairsList.get(namedPairsList.size() - 1);
        assertThat(testNamedPairs.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testNamedPairs.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testNamedPairs.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createNamedPairsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = namedPairsRepository.findAll().size();

        // Create the NamedPairs with an existing ID
        namedPairs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNamedPairsMockMvc.perform(post("/api/named-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(namedPairs)))
            .andExpect(status().isBadRequest());

        // Validate the NamedPairs in the database
        List<NamedPairs> namedPairsList = namedPairsRepository.findAll();
        assertThat(namedPairsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = namedPairsRepository.findAll().size();
        // set the field null
        namedPairs.setGroupName(null);

        // Create the NamedPairs, which fails.

        restNamedPairsMockMvc.perform(post("/api/named-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(namedPairs)))
            .andExpect(status().isBadRequest());

        List<NamedPairs> namedPairsList = namedPairsRepository.findAll();
        assertThat(namedPairsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = namedPairsRepository.findAll().size();
        // set the field null
        namedPairs.setKey(null);

        // Create the NamedPairs, which fails.

        restNamedPairsMockMvc.perform(post("/api/named-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(namedPairs)))
            .andExpect(status().isBadRequest());

        List<NamedPairs> namedPairsList = namedPairsRepository.findAll();
        assertThat(namedPairsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = namedPairsRepository.findAll().size();
        // set the field null
        namedPairs.setValue(null);

        // Create the NamedPairs, which fails.

        restNamedPairsMockMvc.perform(post("/api/named-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(namedPairs)))
            .andExpect(status().isBadRequest());

        List<NamedPairs> namedPairsList = namedPairsRepository.findAll();
        assertThat(namedPairsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNamedPairs() throws Exception {
        // Initialize the database
        namedPairsRepository.saveAndFlush(namedPairs);

        // Get all the namedPairsList
        restNamedPairsMockMvc.perform(get("/api/named-pairs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(namedPairs.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getNamedPairs() throws Exception {
        // Initialize the database
        namedPairsRepository.saveAndFlush(namedPairs);

        // Get the namedPairs
        restNamedPairsMockMvc.perform(get("/api/named-pairs/{id}", namedPairs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(namedPairs.getId().intValue()))
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNamedPairs() throws Exception {
        // Get the namedPairs
        restNamedPairsMockMvc.perform(get("/api/named-pairs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNamedPairs() throws Exception {
        // Initialize the database
        namedPairsService.save(namedPairs);

        int databaseSizeBeforeUpdate = namedPairsRepository.findAll().size();

        // Update the namedPairs
        NamedPairs updatedNamedPairs = namedPairsRepository.findById(namedPairs.getId()).get();
        // Disconnect from session so that the updates on updatedNamedPairs are not directly saved in db
        em.detach(updatedNamedPairs);
        updatedNamedPairs
            .groupName(UPDATED_GROUP_NAME)
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);

        restNamedPairsMockMvc.perform(put("/api/named-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNamedPairs)))
            .andExpect(status().isOk());

        // Validate the NamedPairs in the database
        List<NamedPairs> namedPairsList = namedPairsRepository.findAll();
        assertThat(namedPairsList).hasSize(databaseSizeBeforeUpdate);
        NamedPairs testNamedPairs = namedPairsList.get(namedPairsList.size() - 1);
        assertThat(testNamedPairs.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testNamedPairs.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testNamedPairs.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingNamedPairs() throws Exception {
        int databaseSizeBeforeUpdate = namedPairsRepository.findAll().size();

        // Create the NamedPairs

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNamedPairsMockMvc.perform(put("/api/named-pairs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(namedPairs)))
            .andExpect(status().isBadRequest());

        // Validate the NamedPairs in the database
        List<NamedPairs> namedPairsList = namedPairsRepository.findAll();
        assertThat(namedPairsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNamedPairs() throws Exception {
        // Initialize the database
        namedPairsService.save(namedPairs);

        int databaseSizeBeforeDelete = namedPairsRepository.findAll().size();

        // Delete the namedPairs
        restNamedPairsMockMvc.perform(delete("/api/named-pairs/{id}", namedPairs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<NamedPairs> namedPairsList = namedPairsRepository.findAll();
        assertThat(namedPairsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NamedPairs.class);
        NamedPairs namedPairs1 = new NamedPairs();
        namedPairs1.setId(1L);
        NamedPairs namedPairs2 = new NamedPairs();
        namedPairs2.setId(namedPairs1.getId());
        assertThat(namedPairs1).isEqualTo(namedPairs2);
        namedPairs2.setId(2L);
        assertThat(namedPairs1).isNotEqualTo(namedPairs2);
        namedPairs1.setId(null);
        assertThat(namedPairs1).isNotEqualTo(namedPairs2);
    }
}
