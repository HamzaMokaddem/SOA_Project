package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.PlanetService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.PlanetDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Planet}.
 */
@RestController
@RequestMapping("/api")
public class PlanetResource {

    private final Logger log = LoggerFactory.getLogger(PlanetResource.class);

    private static final String ENTITY_NAME = "planet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanetService planetService;

    public PlanetResource(PlanetService planetService) {
        this.planetService = planetService;
    }

    /**
     * {@code POST  /planets} : Create a new planet.
     *
     * @param planetDTO the planetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planetDTO, or with status {@code 400 (Bad Request)} if the planet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/planets")
    public ResponseEntity<PlanetDTO> createPlanet(@Valid @RequestBody PlanetDTO planetDTO) throws URISyntaxException {
        log.debug("REST request to save Planet : {}", planetDTO);
        if (planetDTO.getId() != null) {
            throw new BadRequestAlertException("A new planet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanetDTO result = planetService.save(planetDTO);
        return ResponseEntity.created(new URI("/api/planets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /planets} : Updates an existing planet.
     *
     * @param planetDTO the planetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planetDTO,
     * or with status {@code 400 (Bad Request)} if the planetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/planets")
    public ResponseEntity<PlanetDTO> updatePlanet(@Valid @RequestBody PlanetDTO planetDTO) throws URISyntaxException {
        log.debug("REST request to update Planet : {}", planetDTO);
        if (planetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanetDTO result = planetService.save(planetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /planets} : get all the planets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planets in body.
     */
    @GetMapping("/planets")
    public ResponseEntity<List<PlanetDTO>> getAllPlanets(Pageable pageable) {
        log.debug("REST request to get a page of Planets");
        Page<PlanetDTO> page = planetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /planets/:id} : get the "id" planet.
     *
     * @param id the id of the planetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/planets/{id}")
    public ResponseEntity<PlanetDTO> getPlanet(@PathVariable Long id) {
        log.debug("REST request to get Planet : {}", id);
        Optional<PlanetDTO> planetDTO = planetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planetDTO);
    }

    /**
     * {@code DELETE  /planets/:id} : delete the "id" planet.
     *
     * @param id the id of the planetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/planets/{id}")
    public ResponseEntity<Void> deletePlanet(@PathVariable Long id) {
        log.debug("REST request to delete Planet : {}", id);
        planetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
