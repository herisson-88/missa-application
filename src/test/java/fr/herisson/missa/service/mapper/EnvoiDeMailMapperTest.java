package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnvoiDeMailMapperTest {

    private EnvoiDeMailMapper envoiDeMailMapper;

    @BeforeEach
    public void setUp() {
        envoiDeMailMapper = new EnvoiDeMailMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(envoiDeMailMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(envoiDeMailMapper.fromId(null)).isNull();
    }
}
