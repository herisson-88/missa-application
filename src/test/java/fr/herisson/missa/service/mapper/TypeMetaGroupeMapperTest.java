package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeMetaGroupeMapperTest {

    private TypeMetaGroupeMapper typeMetaGroupeMapper;

    @BeforeEach
    public void setUp() {
        typeMetaGroupeMapper = new TypeMetaGroupeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(typeMetaGroupeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeMetaGroupeMapper.fromId(null)).isNull();
    }
}
