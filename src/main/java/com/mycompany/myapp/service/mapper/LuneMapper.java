package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.LuneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lune} and its DTO {@link LuneDTO}.
 */
@Mapper(componentModel = "spring", uses = {PlanetMapper.class})
public interface LuneMapper extends EntityMapper<LuneDTO, Lune> {

    @Mapping(source = "planet.id", target = "planetId")
    @Mapping(source = "planet.name", target = "planetName")
    LuneDTO toDto(Lune lune);

    @Mapping(source = "planetId", target = "planet")
    Lune toEntity(LuneDTO luneDTO);

    default Lune fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lune lune = new Lune();
        lune.setId(id);
        return lune;
    }
}
