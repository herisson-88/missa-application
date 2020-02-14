package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.MembreMetaGroupeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MembreMetaGroupe} and its DTO {@link MembreMetaGroupeDTO}.
 */
@Mapper(componentModel = "spring", uses = {MetaGroupeMapper.class, MissaUserMapper.class})
public interface MembreMetaGroupeMapper extends EntityMapper<MembreMetaGroupeDTO, MembreMetaGroupe> {

    @Mapping(source = "groupeMembre.id", target = "groupeMembreId")
    @Mapping(source = "groupeMembre.nom", target = "groupeMembreNom")
    @Mapping(source = "validePar.id", target = "valideParId")
    @Mapping(source = "missaUser.id", target = "missaUserId")
    MembreMetaGroupeDTO toDto(MembreMetaGroupe membreMetaGroupe);

    @Mapping(source = "groupeMembreId", target = "groupeMembre")
    @Mapping(source = "valideParId", target = "validePar")
    @Mapping(source = "missaUserId", target = "missaUser")
    MembreMetaGroupe toEntity(MembreMetaGroupeDTO membreMetaGroupeDTO);

    default MembreMetaGroupe fromId(Long id) {
        if (id == null) {
            return null;
        }
        MembreMetaGroupe membreMetaGroupe = new MembreMetaGroupe();
        membreMetaGroupe.setId(id);
        return membreMetaGroupe;
    }
}
