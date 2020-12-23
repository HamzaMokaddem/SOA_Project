package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.LuneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Lune}.
 */
public interface LuneService {

    /**
     * Save a lune.
     *
     * @param luneDTO the entity to save.
     * @return the persisted entity.
     */
    LuneDTO save(LuneDTO luneDTO);

    /**
     * Get all the lunes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LuneDTO> findAll(Pageable pageable);


    /**
     * Get the "id" lune.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LuneDTO> findOne(Long id);

    /**
     * Delete the "id" lune.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
