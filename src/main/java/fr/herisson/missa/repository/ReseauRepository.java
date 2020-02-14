package fr.herisson.missa.repository;

import fr.herisson.missa.domain.Reseau;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Reseau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReseauRepository extends JpaRepository<Reseau, Long>, JpaSpecificationExecutor<Reseau> {

}
