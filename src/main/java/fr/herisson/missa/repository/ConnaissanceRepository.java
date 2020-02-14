package fr.herisson.missa.repository;

import fr.herisson.missa.domain.Connaissance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Connaissance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConnaissanceRepository extends JpaRepository<Connaissance, Long>, JpaSpecificationExecutor<Connaissance> {

}
