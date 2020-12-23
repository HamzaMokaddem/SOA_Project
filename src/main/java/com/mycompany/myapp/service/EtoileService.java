package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EtoileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Etoile}.
 */
public interface EtoileService {

    /**
     * Save a etoile.
     *
     * @param etoileDTO the entity to save.
     * @return the persisted entity.
     */
    EtoileDTO save(EtoileDTO etoileDTO);

    /**
     * Get all the etoiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EtoileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" etoile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EtoileDTO> findOne(Long id);

    /**
     * Delete the "id" etoile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
