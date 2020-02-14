package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.OrganisateurMetaGroupe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OrganisateurMetaGroupe} entity.
 */
public interface OrganisateurMetaGroupeSearchRepository extends ElasticsearchRepository<OrganisateurMetaGroupe, Long> {
}
