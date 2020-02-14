package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.TypeMetaGroupe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TypeMetaGroupe} entity.
 */
public interface TypeMetaGroupeSearchRepository extends ElasticsearchRepository<TypeMetaGroupe, Long> {
}
