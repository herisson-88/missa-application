package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LienDocMetaGroupeMapperTest {

    private LienDocMetaGroupeMapper lienDocMetaGroupeMapper;

    @BeforeEach
    public void setUp() {
        lienDocMetaGroupeMapper = new LienDocMetaGroupeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(lienDocMetaGroupeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lienDocMetaGroupeMapper.fromId(null)).isNull();
    }
}
