package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.ConnaissanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Connaissance} and its DTO {@link ConnaissanceDTO}.
 */
@Mapper(componentModel = "spring", uses = {MissaUserMapper.class})
public interface ConnaissanceMapper extends EntityMapper<ConnaissanceDTO, Connaissance> {

    @Mapping(source = "connuPar.id", target = "connuParId")
    ConnaissanceDTO toDto(Connaissance connaissance);

    @Mapping(source = "connuParId", target = "connuPar")
    Connaissance toEntity(ConnaissanceDTO connaissanceDTO);

    default Connaissance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Connaissance connaissance = new Connaissance();
        connaissance.setId(id);
        return connaissance;
    }
}
