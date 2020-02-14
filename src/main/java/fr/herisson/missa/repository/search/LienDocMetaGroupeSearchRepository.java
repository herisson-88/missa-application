package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.LienDocMetaGroupe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LienDocMetaGroupe} entity.
 */
public interface LienDocMetaGroupeSearchRepository extends ElasticsearchRepository<LienDocMetaGroupe, Long> {
}
