package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PartageMetaGroupeMapperTest {

    private PartageMetaGroupeMapper partageMetaGroupeMapper;

    @BeforeEach
    public void setUp() {
        partageMetaGroupeMapper = new PartageMetaGroupeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(partageMetaGroupeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partageMetaGroupeMapper.fromId(null)).isNull();
    }
}
