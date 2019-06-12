package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.TrackstockApp;
import com.jugglerapps.stocktrack.domain.Position;
import com.jugglerapps.stocktrack.repository.PositionRepository;
import com.jugglerapps.stocktrack.service.PositionService;
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
 * Integration tests for the {@Link PositionResource} REST controller.
 */
@SpringBootTest(classes = TrackstockApp.class)
public class PositionResourceIT {

    private static final String DEFAULT_POSITION_TRADE_PLAN = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_TRADE_PLAN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_POSITION_OPEN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POSITION_OPEN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_POSITION_OPEN_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_POSITION_OPEN_PRICE = new BigDecimal(2);

    private static final LocalDate DEFAULT_POSITION_CLOSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POSITION_CLOSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_POSITION_CLOSE_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_POSITION_CLOSE_PRICE = new BigDecimal(2);

    private static final Boolean DEFAULT_POSITIO_WIN_LOSS = false;
    private static final Boolean UPDATED_POSITIO_WIN_LOSS = true;

    private static final BigDecimal DEFAULT_POSITION_PROFIT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_POSITION_PROFIT_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_POSITION_CLOSING_THOUGHT = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_CLOSING_THOUGHT = "BBBBBBBBBB";

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PositionService positionService;

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

    private MockMvc restPositionMockMvc;

    private Position position;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PositionResource positionResource = new PositionResource(positionService);
        this.restPositionMockMvc = MockMvcBuilders.standaloneSetup(positionResource)
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
    public static Position createEntity(EntityManager em) {
        Position position = new Position()
            .positionTradePlan(DEFAULT_POSITION_TRADE_PLAN)
            .positionOpenDate(DEFAULT_POSITION_OPEN_DATE)
            .positionOpenPrice(DEFAULT_POSITION_OPEN_PRICE)
            .positionCloseDate(DEFAULT_POSITION_CLOSE_DATE)
            .positionClosePrice(DEFAULT_POSITION_CLOSE_PRICE)
            .positioWinLoss(DEFAULT_POSITIO_WIN_LOSS)
            .positionProfitAmount(DEFAULT_POSITION_PROFIT_AMOUNT)
            .positionClosingThought(DEFAULT_POSITION_CLOSING_THOUGHT);
        return position;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Position createUpdatedEntity(EntityManager em) {
        Position position = new Position()
            .positionTradePlan(UPDATED_POSITION_TRADE_PLAN)
            .positionOpenDate(UPDATED_POSITION_OPEN_DATE)
            .positionOpenPrice(UPDATED_POSITION_OPEN_PRICE)
            .positionCloseDate(UPDATED_POSITION_CLOSE_DATE)
            .positionClosePrice(UPDATED_POSITION_CLOSE_PRICE)
            .positioWinLoss(UPDATED_POSITIO_WIN_LOSS)
            .positionProfitAmount(UPDATED_POSITION_PROFIT_AMOUNT)
            .positionClosingThought(UPDATED_POSITION_CLOSING_THOUGHT);
        return position;
    }

    @BeforeEach
    public void initTest() {
        position = createEntity(em);
    }

    @Test
    @Transactional
    public void createPosition() throws Exception {
        int databaseSizeBeforeCreate = positionRepository.findAll().size();

        // Create the Position
        restPositionMockMvc.perform(post("/api/positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isCreated());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeCreate + 1);
        Position testPosition = positionList.get(positionList.size() - 1);
        assertThat(testPosition.getPositionTradePlan()).isEqualTo(DEFAULT_POSITION_TRADE_PLAN);
        assertThat(testPosition.getPositionOpenDate()).isEqualTo(DEFAULT_POSITION_OPEN_DATE);
        assertThat(testPosition.getPositionOpenPrice()).isEqualTo(DEFAULT_POSITION_OPEN_PRICE);
        assertThat(testPosition.getPositionCloseDate()).isEqualTo(DEFAULT_POSITION_CLOSE_DATE);
        assertThat(testPosition.getPositionClosePrice()).isEqualTo(DEFAULT_POSITION_CLOSE_PRICE);
        assertThat(testPosition.isPositioWinLoss()).isEqualTo(DEFAULT_POSITIO_WIN_LOSS);
        assertThat(testPosition.getPositionProfitAmount()).isEqualTo(DEFAULT_POSITION_PROFIT_AMOUNT);
        assertThat(testPosition.getPositionClosingThought()).isEqualTo(DEFAULT_POSITION_CLOSING_THOUGHT);
    }

    @Test
    @Transactional
    public void createPositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = positionRepository.findAll().size();

        // Create the Position with an existing ID
        position.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPositionMockMvc.perform(post("/api/positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isBadRequest());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPositionTradePlanIsRequired() throws Exception {
        int databaseSizeBeforeTest = positionRepository.findAll().size();
        // set the field null
        position.setPositionTradePlan(null);

        // Create the Position, which fails.

        restPositionMockMvc.perform(post("/api/positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isBadRequest());

        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionOpenDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = positionRepository.findAll().size();
        // set the field null
        position.setPositionOpenDate(null);

        // Create the Position, which fails.

        restPositionMockMvc.perform(post("/api/positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isBadRequest());

        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionOpenPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = positionRepository.findAll().size();
        // set the field null
        position.setPositionOpenPrice(null);

        // Create the Position, which fails.

        restPositionMockMvc.perform(post("/api/positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isBadRequest());

        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPositions() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get all the positionList
        restPositionMockMvc.perform(get("/api/positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(position.getId().intValue())))
            .andExpect(jsonPath("$.[*].positionTradePlan").value(hasItem(DEFAULT_POSITION_TRADE_PLAN.toString())))
            .andExpect(jsonPath("$.[*].positionOpenDate").value(hasItem(DEFAULT_POSITION_OPEN_DATE.toString())))
            .andExpect(jsonPath("$.[*].positionOpenPrice").value(hasItem(DEFAULT_POSITION_OPEN_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].positionCloseDate").value(hasItem(DEFAULT_POSITION_CLOSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].positionClosePrice").value(hasItem(DEFAULT_POSITION_CLOSE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].positioWinLoss").value(hasItem(DEFAULT_POSITIO_WIN_LOSS.booleanValue())))
            .andExpect(jsonPath("$.[*].positionProfitAmount").value(hasItem(DEFAULT_POSITION_PROFIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].positionClosingThought").value(hasItem(DEFAULT_POSITION_CLOSING_THOUGHT.toString())));
    }
    
    @Test
    @Transactional
    public void getPosition() throws Exception {
        // Initialize the database
        positionRepository.saveAndFlush(position);

        // Get the position
        restPositionMockMvc.perform(get("/api/positions/{id}", position.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(position.getId().intValue()))
            .andExpect(jsonPath("$.positionTradePlan").value(DEFAULT_POSITION_TRADE_PLAN.toString()))
            .andExpect(jsonPath("$.positionOpenDate").value(DEFAULT_POSITION_OPEN_DATE.toString()))
            .andExpect(jsonPath("$.positionOpenPrice").value(DEFAULT_POSITION_OPEN_PRICE.intValue()))
            .andExpect(jsonPath("$.positionCloseDate").value(DEFAULT_POSITION_CLOSE_DATE.toString()))
            .andExpect(jsonPath("$.positionClosePrice").value(DEFAULT_POSITION_CLOSE_PRICE.intValue()))
            .andExpect(jsonPath("$.positioWinLoss").value(DEFAULT_POSITIO_WIN_LOSS.booleanValue()))
            .andExpect(jsonPath("$.positionProfitAmount").value(DEFAULT_POSITION_PROFIT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.positionClosingThought").value(DEFAULT_POSITION_CLOSING_THOUGHT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPosition() throws Exception {
        // Get the position
        restPositionMockMvc.perform(get("/api/positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePosition() throws Exception {
        // Initialize the database
        positionService.save(position);

        int databaseSizeBeforeUpdate = positionRepository.findAll().size();

        // Update the position
        Position updatedPosition = positionRepository.findById(position.getId()).get();
        // Disconnect from session so that the updates on updatedPosition are not directly saved in db
        em.detach(updatedPosition);
        updatedPosition
            .positionTradePlan(UPDATED_POSITION_TRADE_PLAN)
            .positionOpenDate(UPDATED_POSITION_OPEN_DATE)
            .positionOpenPrice(UPDATED_POSITION_OPEN_PRICE)
            .positionCloseDate(UPDATED_POSITION_CLOSE_DATE)
            .positionClosePrice(UPDATED_POSITION_CLOSE_PRICE)
            .positioWinLoss(UPDATED_POSITIO_WIN_LOSS)
            .positionProfitAmount(UPDATED_POSITION_PROFIT_AMOUNT)
            .positionClosingThought(UPDATED_POSITION_CLOSING_THOUGHT);

        restPositionMockMvc.perform(put("/api/positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPosition)))
            .andExpect(status().isOk());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
        Position testPosition = positionList.get(positionList.size() - 1);
        assertThat(testPosition.getPositionTradePlan()).isEqualTo(UPDATED_POSITION_TRADE_PLAN);
        assertThat(testPosition.getPositionOpenDate()).isEqualTo(UPDATED_POSITION_OPEN_DATE);
        assertThat(testPosition.getPositionOpenPrice()).isEqualTo(UPDATED_POSITION_OPEN_PRICE);
        assertThat(testPosition.getPositionCloseDate()).isEqualTo(UPDATED_POSITION_CLOSE_DATE);
        assertThat(testPosition.getPositionClosePrice()).isEqualTo(UPDATED_POSITION_CLOSE_PRICE);
        assertThat(testPosition.isPositioWinLoss()).isEqualTo(UPDATED_POSITIO_WIN_LOSS);
        assertThat(testPosition.getPositionProfitAmount()).isEqualTo(UPDATED_POSITION_PROFIT_AMOUNT);
        assertThat(testPosition.getPositionClosingThought()).isEqualTo(UPDATED_POSITION_CLOSING_THOUGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingPosition() throws Exception {
        int databaseSizeBeforeUpdate = positionRepository.findAll().size();

        // Create the Position

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPositionMockMvc.perform(put("/api/positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(position)))
            .andExpect(status().isBadRequest());

        // Validate the Position in the database
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePosition() throws Exception {
        // Initialize the database
        positionService.save(position);

        int databaseSizeBeforeDelete = positionRepository.findAll().size();

        // Delete the position
        restPositionMockMvc.perform(delete("/api/positions/{id}", position.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Position> positionList = positionRepository.findAll();
        assertThat(positionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Position.class);
        Position position1 = new Position();
        position1.setId(1L);
        Position position2 = new Position();
        position2.setId(position1.getId());
        assertThat(position1).isEqualTo(position2);
        position2.setId(2L);
        assertThat(position1).isNotEqualTo(position2);
        position1.setId(null);
        assertThat(position1).isNotEqualTo(position2);
    }
}
