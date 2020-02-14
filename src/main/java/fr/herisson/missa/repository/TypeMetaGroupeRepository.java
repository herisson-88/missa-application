package fr.herisson.missa.repository;

import fr.herisson.missa.domain.TypeMetaGroupe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeMetaGroupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeMetaGroupeRepository extends JpaRepository<TypeMetaGroupe, Long>, JpaSpecificationExecutor<TypeMetaGroupe> {

}
