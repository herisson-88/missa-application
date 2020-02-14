package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.ReseauDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reseau} and its DTO {@link ReseauDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReseauMapper extends EntityMapper<ReseauDTO, Reseau> {


    @Mapping(target = "groupes", ignore = true)
    @Mapping(target = "removeGroupes", ignore = true)
    @Mapping(target = "types", ignore = true)
    @Mapping(target = "removeTypes", ignore = true)
    Reseau toEntity(ReseauDTO reseauDTO);

    default Reseau fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reseau reseau = new Reseau();
        reseau.setId(id);
        return reseau;
    }
}
