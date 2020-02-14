package fr.herisson.missa.repository;

import fr.herisson.missa.domain.LieuMetaGroupe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LieuMetaGroupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LieuMetaGroupeRepository extends JpaRepository<LieuMetaGroupe, Long>, JpaSpecificationExecutor<LieuMetaGroupe> {

}
