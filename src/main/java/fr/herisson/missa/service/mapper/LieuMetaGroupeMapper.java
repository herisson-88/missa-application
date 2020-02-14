package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.LieuMetaGroupeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LieuMetaGroupe} and its DTO {@link LieuMetaGroupeDTO}.
 */
@Mapper(componentModel = "spring", uses = {MetaGroupeMapper.class})
public interface LieuMetaGroupeMapper extends EntityMapper<LieuMetaGroupeDTO, LieuMetaGroupe> {

    @Mapping(source = "groupe.id", target = "groupeId")
    @Mapping(source = "groupe.nom", target = "groupeNom")
    LieuMetaGroupeDTO toDto(LieuMetaGroupe lieuMetaGroupe);

    @Mapping(source = "groupeId", target = "groupe")
    LieuMetaGroupe toEntity(LieuMetaGroupeDTO lieuMetaGroupeDTO);

    default LieuMetaGroupe fromId(Long id) {
        if (id == null) {
            return null;
        }
        LieuMetaGroupe lieuMetaGroupe = new LieuMetaGroupe();
        lieuMetaGroupe.setId(id);
        return lieuMetaGroupe;
    }
}
