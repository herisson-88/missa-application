package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.Reseau;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Reseau} entity.
 */
public interface ReseauSearchRepository extends ElasticsearchRepository<Reseau, Long> {
}
