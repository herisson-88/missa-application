package fr.herisson.missa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link MessageMetaGroupeSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MessageMetaGroupeSearchRepositoryMockConfiguration {

    @MockBean
    private MessageMetaGroupeSearchRepository mockMessageMetaGroupeSearchRepository;

}
