package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Etoile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Etoile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtoileRepository extends JpaRepository<Etoile, Long> {
}
