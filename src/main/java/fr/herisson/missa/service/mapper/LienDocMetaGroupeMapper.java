package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.LienDocMetaGroupeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LienDocMetaGroupe} and its DTO {@link LienDocMetaGroupeDTO}.
 */
@Mapper(componentModel = "spring", uses = {MetaGroupeMapper.class, TypeMetaGroupeMapper.class})
public interface LienDocMetaGroupeMapper extends EntityMapper<LienDocMetaGroupeDTO, LienDocMetaGroupe> {

    @Mapping(source = "groupe.id", target = "groupeId")
    @Mapping(source = "groupe.nom", target = "groupeNom")
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.typeDuGroupe", target = "typeTypeDuGroupe")
    LienDocMetaGroupeDTO toDto(LienDocMetaGroupe lienDocMetaGroupe);

    @Mapping(source = "groupeId", target = "groupe")
    @Mapping(source = "typeId", target = "type")
    LienDocMetaGroupe toEntity(LienDocMetaGroupeDTO lienDocMetaGroupeDTO);

    default LienDocMetaGroupe fromId(Long id) {
        if (id == null) {
            return null;
        }
        LienDocMetaGroupe lienDocMetaGroupe = new LienDocMetaGroupe();
        lienDocMetaGroupe.setId(id);
        return lienDocMetaGroupe;
    }
}
