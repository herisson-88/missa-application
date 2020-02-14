package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class OrganisateurMetaGroupeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganisateurMetaGroupeDTO.class);
        OrganisateurMetaGroupeDTO organisateurMetaGroupeDTO1 = new OrganisateurMetaGroupeDTO();
        organisateurMetaGroupeDTO1.setId(1L);
        OrganisateurMetaGroupeDTO organisateurMetaGroupeDTO2 = new OrganisateurMetaGroupeDTO();
        assertThat(organisateurMetaGroupeDTO1).isNotEqualTo(organisateurMetaGroupeDTO2);
        organisateurMetaGroupeDTO2.setId(organisateurMetaGroupeDTO1.getId());
        assertThat(organisateurMetaGroupeDTO1).isEqualTo(organisateurMetaGroupeDTO2);
        organisateurMetaGroupeDTO2.setId(2L);
        assertThat(organisateurMetaGroupeDTO1).isNotEqualTo(organisateurMetaGroupeDTO2);
        organisateurMetaGroupeDTO1.setId(null);
        assertThat(organisateurMetaGroupeDTO1).isNotEqualTo(organisateurMetaGroupeDTO2);
    }
}
