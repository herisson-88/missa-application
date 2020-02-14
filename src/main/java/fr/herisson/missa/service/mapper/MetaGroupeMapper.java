package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.MetaGroupeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MetaGroupe} and its DTO {@link MetaGroupeDTO}.
 */
@Mapper(componentModel = "spring", uses = {TypeMetaGroupeMapper.class, ReseauMapper.class})
public interface MetaGroupeMapper extends EntityMapper<MetaGroupeDTO, MetaGroupe> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.nom", target = "parentNom")
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "reseau.id", target = "reseauId")
    MetaGroupeDTO toDto(MetaGroupe metaGroupe);

    @Mapping(target = "membres", ignore = true)
    @Mapping(target = "removeMembres", ignore = true)
    @Mapping(target = "dates", ignore = true)
    @Mapping(target = "removeDates", ignore = true)
    @Mapping(target = "lieus", ignore = true)
    @Mapping(target = "removeLieu", ignore = true)
    @Mapping(target = "docs", ignore = true)
    @Mapping(target = "removeDocs", ignore = true)
    @Mapping(target = "coordonneesOrganisateurs", ignore = true)
    @Mapping(target = "removeCoordonneesOrganisateurs", ignore = true)
    @Mapping(target = "sousGroupes", ignore = true)
    @Mapping(target = "removeSousGroupes", ignore = true)
    @Mapping(target = "messagesDuGroupes", ignore = true)
    @Mapping(target = "removeMessagesDuGroupe", ignore = true)
    @Mapping(target = "mails", ignore = true)
    @Mapping(target = "removeMails", ignore = true)
    @Mapping(target = "partagesVers", ignore = true)
    @Mapping(target = "removePartagesVers", ignore = true)
    @Mapping(target = "partagesRecuses", ignore = true)
    @Mapping(target = "removePartagesRecus", ignore = true)
    @Mapping(source = "parentId", target = "parent")
    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "reseauId", target = "reseau")
    @Mapping(target = "messageMailReferents", ignore = true)
    @Mapping(target = "removeMessageMailReferent", ignore = true)
    MetaGroupe toEntity(MetaGroupeDTO metaGroupeDTO);

    default MetaGroupe fromId(Long id) {
        if (id == null) {
            return null;
        }
        MetaGroupe metaGroupe = new MetaGroupe();
        metaGroupe.setId(id);
        return metaGroupe;
    }
}
