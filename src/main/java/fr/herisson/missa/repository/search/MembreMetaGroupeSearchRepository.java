package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.MembreMetaGroupe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MembreMetaGroupe} entity.
 */
public interface MembreMetaGroupeSearchRepository extends ElasticsearchRepository<MembreMetaGroupe, Long> {
}
