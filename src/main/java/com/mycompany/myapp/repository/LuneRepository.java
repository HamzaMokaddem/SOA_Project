package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Lune;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Lune entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LuneRepository extends JpaRepository<Lune, Long> {
}
