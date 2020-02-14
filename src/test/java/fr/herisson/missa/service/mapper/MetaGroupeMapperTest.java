package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MetaGroupeMapperTest {

    private MetaGroupeMapper metaGroupeMapper;

    @BeforeEach
    public void setUp() {
        metaGroupeMapper = new MetaGroupeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(metaGroupeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(metaGroupeMapper.fromId(null)).isNull();
    }
}
