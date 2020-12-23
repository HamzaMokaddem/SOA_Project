package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GalaxyApp;
import com.mycompany.myapp.domain.Planet;
import com.mycompany.myapp.domain.Etoile;
import com.mycompany.myapp.repository.PlanetRepository;
import com.mycompany.myapp.service.PlanetService;
import com.mycompany.myapp.service.dto.PlanetDTO;
import com.mycompany.myapp.service.mapper.PlanetMapper;

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
 * Integration tests for the {@link PlanetResource} REST controller.
 */
@SpringBootTest(classes = GalaxyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private PlanetMapper planetMapper;

    @Autowired
    private PlanetService planetService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanetMockMvc;

    private Planet planet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planet createEntity(EntityManager em) {
        Planet planet = new Planet()
            .name(DEFAULT_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .etat(DEFAULT_ETAT);
        // Add required entity
        Etoile etoile;
        if (TestUtil.findAll(em, Etoile.class).isEmpty()) {
            etoile = EtoileResourceIT.createEntity(em);
            em.persist(etoile);
            em.flush();
        } else {
            etoile = TestUtil.findAll(em, Etoile.class).get(0);
        }
        planet.setEtoile(etoile);
        return planet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planet createUpdatedEntity(EntityManager em) {
        Planet planet = new Planet()
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .etat(UPDATED_ETAT);
        // Add required entity
        Etoile etoile;
        if (TestUtil.findAll(em, Etoile.class).isEmpty()) {
            etoile = EtoileResourceIT.createUpdatedEntity(em);
            em.persist(etoile);
            em.flush();
        } else {
            etoile = TestUtil.findAll(em, Etoile.class).get(0);
        }
        planet.setEtoile(etoile);
        return planet;
    }

    @BeforeEach
    public void initTest() {
        planet = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanet() throws Exception {
        int databaseSizeBeforeCreate = planetRepository.findAll().size();
        // Create the Planet
        PlanetDTO planetDTO = planetMapper.toDto(planet);
        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isCreated());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeCreate + 1);
        Planet testPlanet = planetList.get(planetList.size() - 1);
        assertThat(testPlanet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlanet.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testPlanet.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testPlanet.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    public void createPlanetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planetRepository.findAll().size();

        // Create the Planet with an existing ID
        planet.setId(1L);
        PlanetDTO planetDTO = planetMapper.toDto(planet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planetRepository.findAll().size();
        // set the field null
        planet.setName(null);

        // Create the Planet, which fails.
        PlanetDTO planetDTO = planetMapper.toDto(planet);


        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = planetRepository.findAll().size();
        // set the field null
        planet.setLatitude(null);

        // Create the Planet, which fails.
        PlanetDTO planetDTO = planetMapper.toDto(planet);


        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = planetRepository.findAll().size();
        // set the field null
        planet.setLongitude(null);

        // Create the Planet, which fails.
        PlanetDTO planetDTO = planetMapper.toDto(planet);


        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = planetRepository.findAll().size();
        // set the field null
        planet.setEtat(null);

        // Create the Planet, which fails.
        PlanetDTO planetDTO = planetMapper.toDto(planet);


        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanets() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        // Get all the planetList
        restPlanetMockMvc.perform(get("/api/planets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)));
    }
    
    @Test
    @Transactional
    public void getPlanet() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        // Get the planet
        restPlanetMockMvc.perform(get("/api/planets/{id}", planet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT));
    }
    @Test
    @Transactional
    public void getNonExistingPlanet() throws Exception {
        // Get the planet
        restPlanetMockMvc.perform(get("/api/planets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanet() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        int databaseSizeBeforeUpdate = planetRepository.findAll().size();

        // Update the planet
        Planet updatedPlanet = planetRepository.findById(planet.getId()).get();
        // Disconnect from session so that the updates on updatedPlanet are not directly saved in db
        em.detach(updatedPlanet);
        updatedPlanet
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .etat(UPDATED_ETAT);
        PlanetDTO planetDTO = planetMapper.toDto(updatedPlanet);

        restPlanetMockMvc.perform(put("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isOk());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeUpdate);
        Planet testPlanet = planetList.get(planetList.size() - 1);
        assertThat(testPlanet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlanet.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testPlanet.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testPlanet.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanet() throws Exception {
        int databaseSizeBeforeUpdate = planetRepository.findAll().size();

        // Create the Planet
        PlanetDTO planetDTO = planetMapper.toDto(planet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanetMockMvc.perform(put("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanet() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        int databaseSizeBeforeDelete = planetRepository.findAll().size();

        // Delete the planet
        restPlanetMockMvc.perform(delete("/api/planets/{id}", planet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
