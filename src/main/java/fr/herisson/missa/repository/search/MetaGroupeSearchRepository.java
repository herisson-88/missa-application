package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.MetaGroupe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MetaGroupe} entity.
 */
public interface MetaGroupeSearchRepository extends ElasticsearchRepository<MetaGroupe, Long> {
}
