package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.DateMetaGroupeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DateMetaGroupe} and its DTO {@link DateMetaGroupeDTO}.
 */
@Mapper(componentModel = "spring", uses = {MetaGroupeMapper.class})
public interface DateMetaGroupeMapper extends EntityMapper<DateMetaGroupeDTO, DateMetaGroupe> {

    @Mapping(source = "groupe.id", target = "groupeId")
    @Mapping(source = "groupe.nom", target = "groupeNom")
    DateMetaGroupeDTO toDto(DateMetaGroupe dateMetaGroupe);

    @Mapping(source = "groupeId", target = "groupe")
    DateMetaGroupe toEntity(DateMetaGroupeDTO dateMetaGroupeDTO);

    default DateMetaGroupe fromId(Long id) {
        if (id == null) {
            return null;
        }
        DateMetaGroupe dateMetaGroupe = new DateMetaGroupe();
        dateMetaGroupe.setId(id);
        return dateMetaGroupe;
    }
}
