package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DateMetaGroupeMapperTest {

    private DateMetaGroupeMapper dateMetaGroupeMapper;

    @BeforeEach
    public void setUp() {
        dateMetaGroupeMapper = new DateMetaGroupeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dateMetaGroupeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dateMetaGroupeMapper.fromId(null)).isNull();
    }
}
