package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.PartageMetaGroupeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartageMetaGroupe} and its DTO {@link PartageMetaGroupeDTO}.
 */
@Mapper(componentModel = "spring", uses = {MetaGroupeMapper.class, MissaUserMapper.class})
public interface PartageMetaGroupeMapper extends EntityMapper<PartageMetaGroupeDTO, PartageMetaGroupe> {

    @Mapping(source = "metaGroupePartage.id", target = "metaGroupePartageId")
    @Mapping(source = "metaGroupePartage.nom", target = "metaGroupePartageNom")
    @Mapping(source = "metaGroupeDestinataires.id", target = "metaGroupeDestinatairesId")
    @Mapping(source = "metaGroupeDestinataires.nom", target = "metaGroupeDestinatairesNom")
    @Mapping(source = "auteurPartage.id", target = "auteurPartageId")
    @Mapping(source = "validateurDuPartage.id", target = "validateurDuPartageId")
    PartageMetaGroupeDTO toDto(PartageMetaGroupe partageMetaGroupe);

    @Mapping(source = "metaGroupePartageId", target = "metaGroupePartage")
    @Mapping(source = "metaGroupeDestinatairesId", target = "metaGroupeDestinataires")
    @Mapping(source = "auteurPartageId", target = "auteurPartage")
    @Mapping(source = "validateurDuPartageId", target = "validateurDuPartage")
    PartageMetaGroupe toEntity(PartageMetaGroupeDTO partageMetaGroupeDTO);

    default PartageMetaGroupe fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartageMetaGroupe partageMetaGroupe = new PartageMetaGroupe();
        partageMetaGroupe.setId(id);
        return partageMetaGroupe;
    }
}
