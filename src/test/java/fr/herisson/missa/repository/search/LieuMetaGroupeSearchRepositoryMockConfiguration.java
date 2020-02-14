package fr.herisson.missa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link LieuMetaGroupeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LieuMetaGroupeSearchRepositoryMockConfiguration {

    @MockBean
    private LieuMetaGroupeSearchRepository mockLieuMetaGroupeSearchRepository;

}
