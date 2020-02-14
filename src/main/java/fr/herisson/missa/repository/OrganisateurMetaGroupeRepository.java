package fr.herisson.missa.repository;

import fr.herisson.missa.domain.OrganisateurMetaGroupe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrganisateurMetaGroupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisateurMetaGroupeRepository extends JpaRepository<OrganisateurMetaGroupe, Long>, JpaSpecificationExecutor<OrganisateurMetaGroupe> {

}
