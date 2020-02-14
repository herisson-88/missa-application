package fr.herisson.missa.repository;

import fr.herisson.missa.domain.LienDocMetaGroupe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LienDocMetaGroupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LienDocMetaGroupeRepository extends JpaRepository<LienDocMetaGroupe, Long>, JpaSpecificationExecutor<LienDocMetaGroupe> {

}
