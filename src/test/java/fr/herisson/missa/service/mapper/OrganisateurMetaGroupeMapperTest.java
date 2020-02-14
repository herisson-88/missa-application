package fr.herisson.missa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrganisateurMetaGroupeMapperTest {

    private OrganisateurMetaGroupeMapper organisateurMetaGroupeMapper;

    @BeforeEach
    public void setUp() {
        organisateurMetaGroupeMapper = new OrganisateurMetaGroupeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(organisateurMetaGroupeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(organisateurMetaGroupeMapper.fromId(null)).isNull();
    }
}
