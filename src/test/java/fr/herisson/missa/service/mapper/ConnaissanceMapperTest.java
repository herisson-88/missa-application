package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConnaissanceMapperTest {

    private ConnaissanceMapper connaissanceMapper;

    @BeforeEach
    public void setUp() {
        connaissanceMapper = new ConnaissanceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(connaissanceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(connaissanceMapper.fromId(null)).isNull();
    }
}
