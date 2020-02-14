package fr.herisson.missa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TypeMetaGroupeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TypeMetaGroupeSearchRepositoryMockConfiguration {

    @MockBean
    private TypeMetaGroupeSearchRepository mockTypeMetaGroupeSearchRepository;

}
