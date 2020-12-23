package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PlanetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Planet} and its DTO {@link PlanetDTO}.
 */
@Mapper(componentModel = "spring", uses = {EtoileMapper.class})
public interface PlanetMapper extends EntityMapper<PlanetDTO, Planet> {

    @Mapping(source = "etoile.id", target = "etoileId")
    @Mapping(source = "etoile.name", target = "etoileName")
    PlanetDTO toDto(Planet planet);

    @Mapping(source = "etoileId", target = "etoile")
    @Mapping(target = "lunes", ignore = true)
    @Mapping(target = "removeLune", ignore = true)
    Planet toEntity(PlanetDTO planetDTO);

    default Planet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Planet planet = new Planet();
        planet.setId(id);
        return planet;
    }
}
