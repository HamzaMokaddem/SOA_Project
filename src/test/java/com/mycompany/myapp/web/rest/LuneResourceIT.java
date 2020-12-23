package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.GalaxyApp;
import com.mycompany.myapp.domain.Lune;
import com.mycompany.myapp.domain.Planet;
import com.mycompany.myapp.repository.LuneRepository;
import com.mycompany.myapp.service.LuneService;
import com.mycompany.myapp.service.dto.LuneDTO;
import com.mycompany.myapp.service.mapper.LuneMapper;

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
 * Integration tests for the {@link LuneResource} REST controller.
 */
@SpringBootTest(classes = GalaxyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LuneResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final Float DEFAULT_TAILLE = 1F;
    private static final Float UPDATED_TAILLE = 2F;

    @Autowired
    private LuneRepository luneRepository;

    @Autowired
    private LuneMapper luneMapper;

    @Autowired
    private LuneService luneService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLuneMockMvc;

    private Lune lune;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lune createEntity(EntityManager em) {
        Lune lune = new Lune()
            .name(DEFAULT_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .taille(DEFAULT_TAILLE);
        // Add required entity
        Planet planet;
        if (TestUtil.findAll(em, Planet.class).isEmpty()) {
            planet = PlanetResourceIT.createEntity(em);
            em.persist(planet);
            em.flush();
        } else {
            planet = TestUtil.findAll(em, Planet.class).get(0);
        }
        lune.setPlanet(planet);
        return lune;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lune createUpdatedEntity(EntityManager em) {
        Lune lune = new Lune()
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .taille(UPDATED_TAILLE);
        // Add required entity
        Planet planet;
        if (TestUtil.findAll(em, Planet.class).isEmpty()) {
            planet = PlanetResourceIT.createUpdatedEntity(em);
            em.persist(planet);
            em.flush();
        } else {
            planet = TestUtil.findAll(em, Planet.class).get(0);
        }
        lune.setPlanet(planet);
        return lune;
    }

    @BeforeEach
    public void initTest() {
        lune = createEntity(em);
    }

    @Test
    @Transactional
    public void createLune() throws Exception {
        int databaseSizeBeforeCreate = luneRepository.findAll().size();
        // Create the Lune
        LuneDTO luneDTO = luneMapper.toDto(lune);
        restLuneMockMvc.perform(post("/api/lunes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(luneDTO)))
            .andExpect(status().isCreated());

        // Validate the Lune in the database
        List<Lune> luneList = luneRepository.findAll();
        assertThat(luneList).hasSize(databaseSizeBeforeCreate + 1);
        Lune testLune = luneList.get(luneList.size() - 1);
        assertThat(testLune.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLune.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testLune.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLune.getTaille()).isEqualTo(DEFAULT_TAILLE);
    }

    @Test
    @Transactional
    public void createLuneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = luneRepository.findAll().size();

        // Create the Lune with an existing ID
        lune.setId(1L);
        LuneDTO luneDTO = luneMapper.toDto(lune);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLuneMockMvc.perform(post("/api/lunes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(luneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lune in the database
        List<Lune> luneList = luneRepository.findAll();
        assertThat(luneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = luneRepository.findAll().size();
        // set the field null
        lune.setName(null);

        // Create the Lune, which fails.
        LuneDTO luneDTO = luneMapper.toDto(lune);


        restLuneMockMvc.perform(post("/api/lunes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(luneDTO)))
            .andExpect(status().isBadRequest());

        List<Lune> luneList = luneRepository.findAll();
        assertThat(luneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = luneRepository.findAll().size();
        // set the field null
        lune.setLatitude(null);

        // Create the Lune, which fails.
        LuneDTO luneDTO = luneMapper.toDto(lune);


        restLuneMockMvc.perform(post("/api/lunes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(luneDTO)))
            .andExpect(status().isBadRequest());

        List<Lune> luneList = luneRepository.findAll();
        assertThat(luneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = luneRepository.findAll().size();
        // set the field null
        lune.setLongitude(null);

        // Create the Lune, which fails.
        LuneDTO luneDTO = luneMapper.toDto(lune);


        restLuneMockMvc.perform(post("/api/lunes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(luneDTO)))
            .andExpect(status().isBadRequest());

        List<Lune> luneList = luneRepository.findAll();
        assertThat(luneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTailleIsRequired() throws Exception {
        int databaseSizeBeforeTest = luneRepository.findAll().size();
        // set the field null
        lune.setTaille(null);

        // Create the Lune, which fails.
        LuneDTO luneDTO = luneMapper.toDto(lune);


        restLuneMockMvc.perform(post("/api/lunes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(luneDTO)))
            .andExpect(status().isBadRequest());

        List<Lune> luneList = luneRepository.findAll();
        assertThat(luneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLunes() throws Exception {
        // Initialize the database
        luneRepository.saveAndFlush(lune);

        // Get all the luneList
        restLuneMockMvc.perform(get("/api/lunes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lune.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].taille").value(hasItem(DEFAULT_TAILLE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getLune() throws Exception {
        // Initialize the database
        luneRepository.saveAndFlush(lune);

        // Get the lune
        restLuneMockMvc.perform(get("/api/lunes/{id}", lune.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lune.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.taille").value(DEFAULT_TAILLE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingLune() throws Exception {
        // Get the lune
        restLuneMockMvc.perform(get("/api/lunes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLune() throws Exception {
        // Initialize the database
        luneRepository.saveAndFlush(lune);

        int databaseSizeBeforeUpdate = luneRepository.findAll().size();

        // Update the lune
        Lune updatedLune = luneRepository.findById(lune.getId()).get();
        // Disconnect from session so that the updates on updatedLune are not directly saved in db
        em.detach(updatedLune);
        updatedLune
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .taille(UPDATED_TAILLE);
        LuneDTO luneDTO = luneMapper.toDto(updatedLune);

        restLuneMockMvc.perform(put("/api/lunes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(luneDTO)))
            .andExpect(status().isOk());

        // Validate the Lune in the database
        List<Lune> luneList = luneRepository.findAll();
        assertThat(luneList).hasSize(databaseSizeBeforeUpdate);
        Lune testLune = luneList.get(luneList.size() - 1);
        assertThat(testLune.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLune.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testLune.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLune.getTaille()).isEqualTo(UPDATED_TAILLE);
    }

    @Test
    @Transactional
    public void updateNonExistingLune() throws Exception {
        int databaseSizeBeforeUpdate = luneRepository.findAll().size();

        // Create the Lune
        LuneDTO luneDTO = luneMapper.toDto(lune);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLuneMockMvc.perform(put("/api/lunes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(luneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lune in the database
        List<Lune> luneList = luneRepository.findAll();
        assertThat(luneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLune() throws Exception {
        // Initialize the database
        luneRepository.saveAndFlush(lune);

        int databaseSizeBeforeDelete = luneRepository.findAll().size();

        // Delete the lune
        restLuneMockMvc.perform(delete("/api/lunes/{id}", lune.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lune> luneList = luneRepository.findAll();
        assertThat(luneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
