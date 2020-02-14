package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.OrganisateurMetaGroupeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganisateurMetaGroupe} and its DTO {@link OrganisateurMetaGroupeDTO}.
 */
@Mapper(componentModel = "spring", uses = {MetaGroupeMapper.class})
public interface OrganisateurMetaGroupeMapper extends EntityMapper<OrganisateurMetaGroupeDTO, OrganisateurMetaGroupe> {

    @Mapping(source = "groupe.id", target = "groupeId")
    @Mapping(source = "groupe.nom", target = "groupeNom")
    OrganisateurMetaGroupeDTO toDto(OrganisateurMetaGroupe organisateurMetaGroupe);

    @Mapping(source = "groupeId", target = "groupe")
    OrganisateurMetaGroupe toEntity(OrganisateurMetaGroupeDTO organisateurMetaGroupeDTO);

    default OrganisateurMetaGroupe fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrganisateurMetaGroupe organisateurMetaGroupe = new OrganisateurMetaGroupe();
        organisateurMetaGroupe.setId(id);
        return organisateurMetaGroupe;
    }
}
