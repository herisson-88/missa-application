package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LieuMetaGroupeMapperTest {

    private LieuMetaGroupeMapper lieuMetaGroupeMapper;

    @BeforeEach
    public void setUp() {
        lieuMetaGroupeMapper = new LieuMetaGroupeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(lieuMetaGroupeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lieuMetaGroupeMapper.fromId(null)).isNull();
    }
}
