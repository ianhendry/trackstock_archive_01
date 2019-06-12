package com.jugglerapps.stocktrack.web.rest;

import com.jugglerapps.stocktrack.TrackstockApp;
import com.jugglerapps.stocktrack.domain.ShippingDetails;
import com.jugglerapps.stocktrack.repository.ShippingDetailsRepository;
import com.jugglerapps.stocktrack.service.ShippingDetailsService;
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
import org.springframework.util.Base64Utils;
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

/**
 * Integration tests for the {@Link ShippingDetailsResource} REST controller.
 */
@SpringBootTest(classes = TrackstockApp.class)
public class ShippingDetailsResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_4 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_4 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_5 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_5 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ADDED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ADDED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_INACTIVE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_INACTIVE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_USER_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_USER_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_USER_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_USER_PICTURE_CONTENT_TYPE = "image/png";

    @Autowired
    private ShippingDetailsRepository shippingDetailsRepository;

    @Autowired
    private ShippingDetailsService shippingDetailsService;

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

    private MockMvc restShippingDetailsMockMvc;

    private ShippingDetails shippingDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShippingDetailsResource shippingDetailsResource = new ShippingDetailsResource(shippingDetailsService);
        this.restShippingDetailsMockMvc = MockMvcBuilders.standaloneSetup(shippingDetailsResource)
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
    public static ShippingDetails createEntity(EntityManager em) {
        ShippingDetails shippingDetails = new ShippingDetails()
            .userName(DEFAULT_USER_NAME)
            .email(DEFAULT_EMAIL)
            .name(DEFAULT_NAME)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .address3(DEFAULT_ADDRESS_3)
            .address4(DEFAULT_ADDRESS_4)
            .address5(DEFAULT_ADDRESS_5)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateInactive(DEFAULT_DATE_INACTIVE)
            .userPicture(DEFAULT_USER_PICTURE)
            .userPictureContentType(DEFAULT_USER_PICTURE_CONTENT_TYPE);
        return shippingDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShippingDetails createUpdatedEntity(EntityManager em) {
        ShippingDetails shippingDetails = new ShippingDetails()
            .userName(UPDATED_USER_NAME)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address4(UPDATED_ADDRESS_4)
            .address5(UPDATED_ADDRESS_5)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateInactive(UPDATED_DATE_INACTIVE)
            .userPicture(UPDATED_USER_PICTURE)
            .userPictureContentType(UPDATED_USER_PICTURE_CONTENT_TYPE);
        return shippingDetails;
    }

    @BeforeEach
    public void initTest() {
        shippingDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createShippingDetails() throws Exception {
        int databaseSizeBeforeCreate = shippingDetailsRepository.findAll().size();

        // Create the ShippingDetails
        restShippingDetailsMockMvc.perform(post("/api/shipping-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingDetails)))
            .andExpect(status().isCreated());

        // Validate the ShippingDetails in the database
        List<ShippingDetails> shippingDetailsList = shippingDetailsRepository.findAll();
        assertThat(shippingDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ShippingDetails testShippingDetails = shippingDetailsList.get(shippingDetailsList.size() - 1);
        assertThat(testShippingDetails.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testShippingDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testShippingDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShippingDetails.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testShippingDetails.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testShippingDetails.getAddress3()).isEqualTo(DEFAULT_ADDRESS_3);
        assertThat(testShippingDetails.getAddress4()).isEqualTo(DEFAULT_ADDRESS_4);
        assertThat(testShippingDetails.getAddress5()).isEqualTo(DEFAULT_ADDRESS_5);
        assertThat(testShippingDetails.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testShippingDetails.getDateInactive()).isEqualTo(DEFAULT_DATE_INACTIVE);
        assertThat(testShippingDetails.getUserPicture()).isEqualTo(DEFAULT_USER_PICTURE);
        assertThat(testShippingDetails.getUserPictureContentType()).isEqualTo(DEFAULT_USER_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createShippingDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shippingDetailsRepository.findAll().size();

        // Create the ShippingDetails with an existing ID
        shippingDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippingDetailsMockMvc.perform(post("/api/shipping-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingDetails in the database
        List<ShippingDetails> shippingDetailsList = shippingDetailsRepository.findAll();
        assertThat(shippingDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShippingDetails() throws Exception {
        // Initialize the database
        shippingDetailsRepository.saveAndFlush(shippingDetails);

        // Get all the shippingDetailsList
        restShippingDetailsMockMvc.perform(get("/api/shipping-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3.toString())))
            .andExpect(jsonPath("$.[*].address4").value(hasItem(DEFAULT_ADDRESS_4.toString())))
            .andExpect(jsonPath("$.[*].address5").value(hasItem(DEFAULT_ADDRESS_5.toString())))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].dateInactive").value(hasItem(DEFAULT_DATE_INACTIVE.toString())))
            .andExpect(jsonPath("$.[*].userPictureContentType").value(hasItem(DEFAULT_USER_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].userPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_USER_PICTURE))));
    }
    
    @Test
    @Transactional
    public void getShippingDetails() throws Exception {
        // Initialize the database
        shippingDetailsRepository.saveAndFlush(shippingDetails);

        // Get the shippingDetails
        restShippingDetailsMockMvc.perform(get("/api/shipping-details/{id}", shippingDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shippingDetails.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.address3").value(DEFAULT_ADDRESS_3.toString()))
            .andExpect(jsonPath("$.address4").value(DEFAULT_ADDRESS_4.toString()))
            .andExpect(jsonPath("$.address5").value(DEFAULT_ADDRESS_5.toString()))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()))
            .andExpect(jsonPath("$.dateInactive").value(DEFAULT_DATE_INACTIVE.toString()))
            .andExpect(jsonPath("$.userPictureContentType").value(DEFAULT_USER_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.userPicture").value(Base64Utils.encodeToString(DEFAULT_USER_PICTURE)));
    }

    @Test
    @Transactional
    public void getNonExistingShippingDetails() throws Exception {
        // Get the shippingDetails
        restShippingDetailsMockMvc.perform(get("/api/shipping-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippingDetails() throws Exception {
        // Initialize the database
        shippingDetailsService.save(shippingDetails);

        int databaseSizeBeforeUpdate = shippingDetailsRepository.findAll().size();

        // Update the shippingDetails
        ShippingDetails updatedShippingDetails = shippingDetailsRepository.findById(shippingDetails.getId()).get();
        // Disconnect from session so that the updates on updatedShippingDetails are not directly saved in db
        em.detach(updatedShippingDetails);
        updatedShippingDetails
            .userName(UPDATED_USER_NAME)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address4(UPDATED_ADDRESS_4)
            .address5(UPDATED_ADDRESS_5)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateInactive(UPDATED_DATE_INACTIVE)
            .userPicture(UPDATED_USER_PICTURE)
            .userPictureContentType(UPDATED_USER_PICTURE_CONTENT_TYPE);

        restShippingDetailsMockMvc.perform(put("/api/shipping-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShippingDetails)))
            .andExpect(status().isOk());

        // Validate the ShippingDetails in the database
        List<ShippingDetails> shippingDetailsList = shippingDetailsRepository.findAll();
        assertThat(shippingDetailsList).hasSize(databaseSizeBeforeUpdate);
        ShippingDetails testShippingDetails = shippingDetailsList.get(shippingDetailsList.size() - 1);
        assertThat(testShippingDetails.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testShippingDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testShippingDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShippingDetails.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testShippingDetails.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testShippingDetails.getAddress3()).isEqualTo(UPDATED_ADDRESS_3);
        assertThat(testShippingDetails.getAddress4()).isEqualTo(UPDATED_ADDRESS_4);
        assertThat(testShippingDetails.getAddress5()).isEqualTo(UPDATED_ADDRESS_5);
        assertThat(testShippingDetails.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testShippingDetails.getDateInactive()).isEqualTo(UPDATED_DATE_INACTIVE);
        assertThat(testShippingDetails.getUserPicture()).isEqualTo(UPDATED_USER_PICTURE);
        assertThat(testShippingDetails.getUserPictureContentType()).isEqualTo(UPDATED_USER_PICTURE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingShippingDetails() throws Exception {
        int databaseSizeBeforeUpdate = shippingDetailsRepository.findAll().size();

        // Create the ShippingDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShippingDetailsMockMvc.perform(put("/api/shipping-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingDetails in the database
        List<ShippingDetails> shippingDetailsList = shippingDetailsRepository.findAll();
        assertThat(shippingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShippingDetails() throws Exception {
        // Initialize the database
        shippingDetailsService.save(shippingDetails);

        int databaseSizeBeforeDelete = shippingDetailsRepository.findAll().size();

        // Delete the shippingDetails
        restShippingDetailsMockMvc.perform(delete("/api/shipping-details/{id}", shippingDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ShippingDetails> shippingDetailsList = shippingDetailsRepository.findAll();
        assertThat(shippingDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingDetails.class);
        ShippingDetails shippingDetails1 = new ShippingDetails();
        shippingDetails1.setId(1L);
        ShippingDetails shippingDetails2 = new ShippingDetails();
        shippingDetails2.setId(shippingDetails1.getId());
        assertThat(shippingDetails1).isEqualTo(shippingDetails2);
        shippingDetails2.setId(2L);
        assertThat(shippingDetails1).isNotEqualTo(shippingDetails2);
        shippingDetails1.setId(null);
        assertThat(shippingDetails1).isNotEqualTo(shippingDetails2);
    }
}
