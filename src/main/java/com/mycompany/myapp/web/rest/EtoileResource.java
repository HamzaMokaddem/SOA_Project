package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.EtoileService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.EtoileDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Etoile}.
 */
@RestController
@RequestMapping("/api")
public class EtoileResource {

    private final Logger log = LoggerFactory.getLogger(EtoileResource.class);

    private static final String ENTITY_NAME = "etoile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtoileService etoileService;

    public EtoileResource(EtoileService etoileService) {
        this.etoileService = etoileService;
    }

    /**
     * {@code POST  /etoiles} : Create a new etoile.
     *
     * @param etoileDTO the etoileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etoileDTO, or with status {@code 400 (Bad Request)} if the etoile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etoiles")
    public ResponseEntity<EtoileDTO> createEtoile(@Valid @RequestBody EtoileDTO etoileDTO) throws URISyntaxException {
        log.debug("REST request to save Etoile : {}", etoileDTO);
        if (etoileDTO.getId() != null) {
            throw new BadRequestAlertException("A new etoile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtoileDTO result = etoileService.save(etoileDTO);
        return ResponseEntity.created(new URI("/api/etoiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etoiles} : Updates an existing etoile.
     *
     * @param etoileDTO the etoileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etoileDTO,
     * or with status {@code 400 (Bad Request)} if the etoileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etoileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etoiles")
    public ResponseEntity<EtoileDTO> updateEtoile(@Valid @RequestBody EtoileDTO etoileDTO) throws URISyntaxException {
        log.debug("REST request to update Etoile : {}", etoileDTO);
        if (etoileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtoileDTO result = etoileService.save(etoileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etoileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etoiles} : get all the etoiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etoiles in body.
     */
    @GetMapping("/etoiles")
    public ResponseEntity<List<EtoileDTO>> getAllEtoiles(Pageable pageable) {
        log.debug("REST request to get a page of Etoiles");
        Page<EtoileDTO> page = etoileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etoiles/:id} : get the "id" etoile.
     *
     * @param id the id of the etoileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etoileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etoiles/{id}")
    public ResponseEntity<EtoileDTO> getEtoile(@PathVariable Long id) {
        log.debug("REST request to get Etoile : {}", id);
        Optional<EtoileDTO> etoileDTO = etoileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etoileDTO);
    }

    /**
     * {@code DELETE  /etoiles/:id} : delete the "id" etoile.
     *
     * @param id the id of the etoileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etoiles/{id}")
    public ResponseEntity<Void> deleteEtoile(@PathVariable Long id) {
        log.debug("REST request to delete Etoile : {}", id);
        etoileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
