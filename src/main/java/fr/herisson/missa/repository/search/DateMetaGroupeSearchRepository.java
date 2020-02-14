package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.DateMetaGroupe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DateMetaGroupe} entity.
 */
public interface DateMetaGroupeSearchRepository extends ElasticsearchRepository<DateMetaGroupe, Long> {
}
