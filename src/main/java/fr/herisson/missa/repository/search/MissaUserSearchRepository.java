package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.MissaUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MissaUser} entity.
 */
public interface MissaUserSearchRepository extends ElasticsearchRepository<MissaUser, Long> {
}
