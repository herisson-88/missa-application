package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.PartageMetaGroupe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PartageMetaGroupe} entity.
 */
public interface PartageMetaGroupeSearchRepository extends ElasticsearchRepository<PartageMetaGroupe, Long> {
}
