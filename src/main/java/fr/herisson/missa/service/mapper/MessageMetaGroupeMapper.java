package fr.herisson.missa.service.mapper;


import fr.herisson.missa.domain.*;
import fr.herisson.missa.service.dto.MessageMetaGroupeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MessageMetaGroupe} and its DTO {@link MessageMetaGroupeDTO}.
 */
@Mapper(componentModel = "spring", uses = {MissaUserMapper.class, MetaGroupeMapper.class})
public interface MessageMetaGroupeMapper extends EntityMapper<MessageMetaGroupeDTO, MessageMetaGroupe> {

    @Mapping(source = "auteur.id", target = "auteurId")
    @Mapping(source = "groupe.id", target = "groupeId")
    @Mapping(source = "groupe.nom", target = "groupeNom")
    MessageMetaGroupeDTO toDto(MessageMetaGroupe messageMetaGroupe);

    @Mapping(source = "auteurId", target = "auteur")
    @Mapping(source = "groupeId", target = "groupe")
    MessageMetaGroupe toEntity(MessageMetaGroupeDTO messageMetaGroupeDTO);

    default MessageMetaGroupe fromId(Long id) {
        if (id == null) {
            return null;
        }
        MessageMetaGroupe messageMetaGroupe = new MessageMetaGroupe();
        messageMetaGroupe.setId(id);
        return messageMetaGroupe;
    }
}
