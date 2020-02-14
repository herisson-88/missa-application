package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.EnvoiDeMail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EnvoiDeMail} entity.
 */
public interface EnvoiDeMailSearchRepository extends ElasticsearchRepository<EnvoiDeMail, Long> {
}
