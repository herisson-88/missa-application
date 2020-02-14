package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.Connaissance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Connaissance} entity.
 */
public interface ConnaissanceSearchRepository extends ElasticsearchRepository<Connaissance, Long> {
}
