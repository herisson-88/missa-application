package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.TypeMetaGroupeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeMetaGroupe} and its DTO {@link TypeMetaGroupeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReseauMapper.class})
public interface TypeMetaGroupeMapper extends EntityMapper<TypeMetaGroupeDTO, TypeMetaGroupe> {

    @Mapping(source = "reseau.id", target = "reseauId")
    TypeMetaGroupeDTO toDto(TypeMetaGroupe typeMetaGroupe);

    @Mapping(target = "defaultDocs", ignore = true)
    @Mapping(target = "removeDefaultDocs", ignore = true)
    @Mapping(target = "groupes", ignore = true)
    @Mapping(target = "removeGroupes", ignore = true)
    @Mapping(source = "reseauId", target = "reseau")
    TypeMetaGroupe toEntity(TypeMetaGroupeDTO typeMetaGroupeDTO);

    default TypeMetaGroupe fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeMetaGroupe typeMetaGroupe = new TypeMetaGroupe();
        typeMetaGroupe.setId(id);
        return typeMetaGroupe;
    }
}
