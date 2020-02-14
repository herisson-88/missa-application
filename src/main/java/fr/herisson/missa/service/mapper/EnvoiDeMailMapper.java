package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.EnvoiDeMailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnvoiDeMail} and its DTO {@link EnvoiDeMailDTO}.
 */
@Mapper(componentModel = "spring", uses = {MetaGroupeMapper.class, MissaUserMapper.class})
public interface EnvoiDeMailMapper extends EntityMapper<EnvoiDeMailDTO, EnvoiDeMail> {

    @Mapping(source = "admin.id", target = "adminId")
    @Mapping(source = "groupeDuMail.id", target = "groupeDuMailId")
    @Mapping(source = "groupeDuMail.nom", target = "groupeDuMailNom")
    EnvoiDeMailDTO toDto(EnvoiDeMail envoiDeMail);

    @Mapping(target = "removeGroupePartageParMail", ignore = true)
    @Mapping(source = "adminId", target = "admin")
    @Mapping(source = "groupeDuMailId", target = "groupeDuMail")
    EnvoiDeMail toEntity(EnvoiDeMailDTO envoiDeMailDTO);

    default EnvoiDeMail fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnvoiDeMail envoiDeMail = new EnvoiDeMail();
        envoiDeMail.setId(id);
        return envoiDeMail;
    }
}
