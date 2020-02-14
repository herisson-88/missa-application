package fr.herisson.missa.repository;

import fr.herisson.missa.domain.MissaUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MissaUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MissaUserRepository extends JpaRepository<MissaUser, Long>, JpaSpecificationExecutor<MissaUser> {

}
