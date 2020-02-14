package fr.herisson.missa.repository;

import fr.herisson.missa.domain.DateMetaGroupe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DateMetaGroupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DateMetaGroupeRepository extends JpaRepository<DateMetaGroupe, Long>, JpaSpecificationExecutor<DateMetaGroupe> {

}
