package fr.herisson.missa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link MissaUserSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MissaUserSearchRepositoryMockConfiguration {

    @MockBean
    private MissaUserSearchRepository mockMissaUserSearchRepository;

}
