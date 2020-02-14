package fr.herisson.missa.repository;

import fr.herisson.missa.domain.MetaGroupe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MetaGroupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetaGroupeRepository extends JpaRepository<MetaGroupe, Long>, JpaSpecificationExecutor<MetaGroupe> {

}
