package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MissaUserMapperTest {

    private MissaUserMapper missaUserMapper;

    @BeforeEach
    public void setUp() {
        missaUserMapper = new MissaUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(missaUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(missaUserMapper.fromId(null)).isNull();
    }
}
