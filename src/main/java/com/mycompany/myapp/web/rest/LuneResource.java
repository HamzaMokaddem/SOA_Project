package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.LuneService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.LuneDTO;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Lune}.
 */
@RestController
@RequestMapping("/api")
public class LuneResource {

    private final Logger log = LoggerFactory.getLogger(LuneResource.class);

    private static final String ENTITY_NAME = "lune";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LuneService luneService;

    public LuneResource(LuneService luneService) {
        this.luneService = luneService;
    }

    /**
     * {@code POST  /lunes} : Create a new lune.
     *
     * @param luneDTO the luneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new luneDTO, or with status {@code 400 (Bad Request)} if the lune has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lunes")
    public ResponseEntity<LuneDTO> createLune(@Valid @RequestBody LuneDTO luneDTO) throws URISyntaxException {
        log.debug("REST request to save Lune : {}", luneDTO);
        if (luneDTO.getId() != null) {
            throw new BadRequestAlertException("A new lune cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LuneDTO result = luneService.save(luneDTO);
        return ResponseEntity.created(new URI("/api/lunes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lunes} : Updates an existing lune.
     *
     * @param luneDTO the luneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated luneDTO,
     * or with status {@code 400 (Bad Request)} if the luneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the luneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lunes")
    public ResponseEntity<LuneDTO> updateLune(@Valid @RequestBody LuneDTO luneDTO) throws URISyntaxException {
        log.debug("REST request to update Lune : {}", luneDTO);
        if (luneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LuneDTO result = luneService.save(luneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, luneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lunes} : get all the lunes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lunes in body.
     */
    @GetMapping("/lunes")
    public ResponseEntity<List<LuneDTO>> getAllLunes(Pageable pageable) {
        log.debug("REST request to get a page of Lunes");
        Page<LuneDTO> page = luneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lunes/:id} : get the "id" lune.
     *
     * @param id the id of the luneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the luneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lunes/{id}")
    public ResponseEntity<LuneDTO> getLune(@PathVariable Long id) {
        log.debug("REST request to get Lune : {}", id);
        Optional<LuneDTO> luneDTO = luneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(luneDTO);
    }

    /**
     * {@code DELETE  /lunes/:id} : delete the "id" lune.
     *
     * @param id the id of the luneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lunes/{id}")
    public ResponseEntity<Void> deleteLune(@PathVariable Long id) {
        log.debug("REST request to delete Lune : {}", id);
        luneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
