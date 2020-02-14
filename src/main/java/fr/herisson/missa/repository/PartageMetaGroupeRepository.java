package fr.herisson.missa.repository;

import fr.herisson.missa.domain.PartageMetaGroupe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PartageMetaGroupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartageMetaGroupeRepository extends JpaRepository<PartageMetaGroupe, Long>, JpaSpecificationExecutor<PartageMetaGroupe> {

}
