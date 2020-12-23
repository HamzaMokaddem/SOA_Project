package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GalaxyApp;
import com.mycompany.myapp.domain.Etoile;
import com.mycompany.myapp.repository.EtoileRepository;
import com.mycompany.myapp.service.EtoileService;
import com.mycompany.myapp.service.dto.EtoileDTO;
import com.mycompany.myapp.service.mapper.EtoileMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EtoileResource} REST controller.
 */
@SpringBootTest(classes = GalaxyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EtoileResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    @Autowired
    private EtoileRepository etoileRepository;

    @Autowired
    private EtoileMapper etoileMapper;

    @Autowired
    private EtoileService etoileService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtoileMockMvc;

    private Etoile etoile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etoile createEntity(EntityManager em) {
        Etoile etoile = new Etoile()
            .name(DEFAULT_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return etoile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etoile createUpdatedEntity(EntityManager em) {
        Etoile etoile = new Etoile()
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        return etoile;
    }

    @BeforeEach
    public void initTest() {
        etoile = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtoile() throws Exception {
        int databaseSizeBeforeCreate = etoileRepository.findAll().size();
        // Create the Etoile
        EtoileDTO etoileDTO = etoileMapper.toDto(etoile);
        restEtoileMockMvc.perform(post("/api/etoiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etoileDTO)))
            .andExpect(status().isCreated());

        // Validate the Etoile in the database
        List<Etoile> etoileList = etoileRepository.findAll();
        assertThat(etoileList).hasSize(databaseSizeBeforeCreate + 1);
        Etoile testEtoile = etoileList.get(etoileList.size() - 1);
        assertThat(testEtoile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEtoile.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testEtoile.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createEtoileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etoileRepository.findAll().size();

        // Create the Etoile with an existing ID
        etoile.setId(1L);
        EtoileDTO etoileDTO = etoileMapper.toDto(etoile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtoileMockMvc.perform(post("/api/etoiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etoileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Etoile in the database
        List<Etoile> etoileList = etoileRepository.findAll();
        assertThat(etoileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = etoileRepository.findAll().size();
        // set the field null
        etoile.setName(null);

        // Create the Etoile, which fails.
        EtoileDTO etoileDTO = etoileMapper.toDto(etoile);


        restEtoileMockMvc.perform(post("/api/etoiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etoileDTO)))
            .andExpect(status().isBadRequest());

        List<Etoile> etoileList = etoileRepository.findAll();
        assertThat(etoileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = etoileRepository.findAll().size();
        // set the field null
        etoile.setLatitude(null);

        // Create the Etoile, which fails.
        EtoileDTO etoileDTO = etoileMapper.toDto(etoile);


        restEtoileMockMvc.perform(post("/api/etoiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etoileDTO)))
            .andExpect(status().isBadRequest());

        List<Etoile> etoileList = etoileRepository.findAll();
        assertThat(etoileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = etoileRepository.findAll().size();
        // set the field null
        etoile.setLongitude(null);

        // Create the Etoile, which fails.
        EtoileDTO etoileDTO = etoileMapper.toDto(etoile);


        restEtoileMockMvc.perform(post("/api/etoiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etoileDTO)))
            .andExpect(status().isBadRequest());

        List<Etoile> etoileList = etoileRepository.findAll();
        assertThat(etoileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEtoiles() throws Exception {
        // Initialize the database
        etoileRepository.saveAndFlush(etoile);

        // Get all the etoileList
        restEtoileMockMvc.perform(get("/api/etoiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etoile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getEtoile() throws Exception {
        // Initialize the database
        etoileRepository.saveAndFlush(etoile);

        // Get the etoile
        restEtoileMockMvc.perform(get("/api/etoiles/{id}", etoile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etoile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingEtoile() throws Exception {
        // Get the etoile
        restEtoileMockMvc.perform(get("/api/etoiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtoile() throws Exception {
        // Initialize the database
        etoileRepository.saveAndFlush(etoile);

        int databaseSizeBeforeUpdate = etoileRepository.findAll().size();

        // Update the etoile
        Etoile updatedEtoile = etoileRepository.findById(etoile.getId()).get();
        // Disconnect from session so that the updates on updatedEtoile are not directly saved in db
        em.detach(updatedEtoile);
        updatedEtoile
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        EtoileDTO etoileDTO = etoileMapper.toDto(updatedEtoile);

        restEtoileMockMvc.perform(put("/api/etoiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etoileDTO)))
            .andExpect(status().isOk());

        // Validate the Etoile in the database
        List<Etoile> etoileList = etoileRepository.findAll();
        assertThat(etoileList).hasSize(databaseSizeBeforeUpdate);
        Etoile testEtoile = etoileList.get(etoileList.size() - 1);
        assertThat(testEtoile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEtoile.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEtoile.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingEtoile() throws Exception {
        int databaseSizeBeforeUpdate = etoileRepository.findAll().size();

        // Create the Etoile
        EtoileDTO etoileDTO = etoileMapper.toDto(etoile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtoileMockMvc.perform(put("/api/etoiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etoileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Etoile in the database
        List<Etoile> etoileList = etoileRepository.findAll();
        assertThat(etoileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtoile() throws Exception {
        // Initialize the database
        etoileRepository.saveAndFlush(etoile);

        int databaseSizeBeforeDelete = etoileRepository.findAll().size();

        // Delete the etoile
        restEtoileMockMvc.perform(delete("/api/etoiles/{id}", etoile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Etoile> etoileList = etoileRepository.findAll();
        assertThat(etoileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
