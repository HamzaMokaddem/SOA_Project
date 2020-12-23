package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PlanetDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Planet}.
 */
public interface PlanetService {

    /**
     * Save a planet.
     *
     * @param planetDTO the entity to save.
     * @return the persisted entity.
     */
    PlanetDTO save(PlanetDTO planetDTO);

    /**
     * Get all the planets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanetDTO> findAll(Pageable pageable);


    /**
     * Get the "id" planet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanetDTO> findOne(Long id);

    /**
     * Delete the "id" planet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
