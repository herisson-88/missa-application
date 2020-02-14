package fr.herisson.missa.repository;

import fr.herisson.missa.domain.MembreMetaGroupe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MembreMetaGroupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembreMetaGroupeRepository extends JpaRepository<MembreMetaGroupe, Long>, JpaSpecificationExecutor<MembreMetaGroupe> {

}
