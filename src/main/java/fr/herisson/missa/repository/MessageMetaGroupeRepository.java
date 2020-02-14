package fr.herisson.missa.repository;

import fr.herisson.missa.domain.MessageMetaGroupe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MessageMetaGroupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageMetaGroupeRepository extends JpaRepository<MessageMetaGroupe, Long>, JpaSpecificationExecutor<MessageMetaGroupe> {

}
