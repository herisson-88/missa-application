package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ReseauMapperTest {

    private ReseauMapper reseauMapper;

    @BeforeEach
    public void setUp() {
        reseauMapper = new ReseauMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(reseauMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(reseauMapper.fromId(null)).isNull();
    }
}
