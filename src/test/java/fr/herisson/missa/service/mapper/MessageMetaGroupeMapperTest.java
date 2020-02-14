package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageMetaGroupeMapperTest {

    private MessageMetaGroupeMapper messageMetaGroupeMapper;

    @BeforeEach
    public void setUp() {
        messageMetaGroupeMapper = new MessageMetaGroupeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(messageMetaGroupeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(messageMetaGroupeMapper.fromId(null)).isNull();
    }
}
