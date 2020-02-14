package fr.herisson.missa.repository.search;

import fr.herisson.missa.domain.LieuMetaGroupe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LieuMetaGroupe} entity.
 */
public interface LieuMetaGroupeSearchRepository extends ElasticsearchRepository<LieuMetaGroupe, Long> {
}
