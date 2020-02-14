package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.MessageMetaGroupe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MessageMetaGroupe} entity.
 */
public interface MessageMetaGroupeSearchRepository extends ElasticsearchRepository<MessageMetaGroupe, Long> {
}
