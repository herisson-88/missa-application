package fr.herisson.missa.repository;

import fr.herisson.missa.domain.EnvoiDeMail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the EnvoiDeMail entity.
 */
@Repository
public interface EnvoiDeMailRepository extends JpaRepository<EnvoiDeMail, Long>, JpaSpecificationExecutor<EnvoiDeMail> {

    @Query(value = "select distinct envoiDeMail from EnvoiDeMail envoiDeMail left join fetch envoiDeMail.groupePartageParMails",
        countQuery = "select count(distinct envoiDeMail) from EnvoiDeMail envoiDeMail")
    Page<EnvoiDeMail> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct envoiDeMail from EnvoiDeMail envoiDeMail left join fetch envoiDeMail.groupePartageParMails")
    List<EnvoiDeMail> findAllWithEagerRelationships();

    @Query("select envoiDeMail from EnvoiDeMail envoiDeMail left join fetch envoiDeMail.groupePartageParMails where envoiDeMail.id =:id")
    Optional<EnvoiDeMail> findOneWithEagerRelationships(@Param("id") Long id);

}
