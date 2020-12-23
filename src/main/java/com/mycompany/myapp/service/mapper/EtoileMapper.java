package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EtoileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Etoile} and its DTO {@link EtoileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EtoileMapper extends EntityMapper<EtoileDTO, Etoile> {


    @Mapping(target = "planets", ignore = true)
    @Mapping(target = "removePlanet", ignore = true)
    Etoile toEntity(EtoileDTO etoileDTO);

    default Etoile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Etoile etoile = new Etoile();
        etoile.setId(id);
        return etoile;
    }
}
