package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.MissaUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MissaUser} and its DTO {@link MissaUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MissaUserMapper extends EntityMapper<MissaUserDTO, MissaUser> {

    @Mapping(source = "user.id", target = "userId")
    MissaUserDTO toDto(MissaUser missaUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "connais", ignore = true)
    @Mapping(target = "removeConnais", ignore = true)
    @Mapping(target = "membreValides", ignore = true)
    @Mapping(target = "removeMembreValides", ignore = true)
    @Mapping(target = "membres", ignore = true)
    @Mapping(target = "removeMembres", ignore = true)
    @Mapping(target = "messagesDeMois", ignore = true)
    @Mapping(target = "removeMessagesDeMoi", ignore = true)
    @Mapping(target = "mails", ignore = true)
    @Mapping(target = "removeMails", ignore = true)
    @Mapping(target = "demandesPartages", ignore = true)
    @Mapping(target = "removeDemandesPartages", ignore = true)
    @Mapping(target = "aValidePartages", ignore = true)
    @Mapping(target = "removeAValidePartages", ignore = true)
    MissaUser toEntity(MissaUserDTO missaUserDTO);

    default MissaUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        MissaUser missaUser = new MissaUser();
        missaUser.setId(id);
        return missaUser;
    }
}
