package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MembreMetaGroupeMapperTest {

    private MembreMetaGroupeMapper membreMetaGroupeMapper;

    @BeforeEach
    public void setUp() {
        membreMetaGroupeMapper = new MembreMetaGroupeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(membreMetaGroupeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(membreMetaGroupeMapper.fromId(null)).isNull();
    }
}
